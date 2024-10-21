class c16755434 {

    public static int getContentLength(URL urlFileLocation) {
        HttpURLConnection connFile = null;
        int iFileSize = -1;
        try {
            connFile = (HttpURLConnection) urlFileLocation.openConnection();
            connFile.setDoInput(true);
            InputStream is = connFile.getInputStream();
            iFileSize = connFile.getContentLength();
            is.close();
            connFile.disconnect();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return iFileSize;
    }
}
