class c4975452 {

    private InputStream openStream(URL url, ProgressListener listener, int minProgress, int maxProgress) throws IORuntimeException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(false);
        String lengthStr = con.getHeaderField("Content-Length");
        InputStream urlIn;
        try {
            urlIn = con.getInputStream();
        } catch (IORuntimeException e) {
            throw new IORuntimeException(con.getResponseCode() + ": " + con.getResponseMessage(), e);
        }
        if (lengthStr == null || lengthStr.isEmpty()) {
            LogService.getRoot().warning("Server did not send content length.");
            return urlIn;
        } else {
            try {
                long length = Long.parseLong(lengthStr);
                return new ProgressReportingInputStream(urlIn, listener, minProgress, maxProgress, length);
            } catch (NumberFormatRuntimeException e) {
                LogService.getRoot().log(Level.WARNING, "Server sent illegal content length: " + lengthStr, e);
                return urlIn;
            }
        }
    }
}
