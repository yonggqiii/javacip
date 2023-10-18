class c13610451 {

    private Element getXmlFromGeoNetwork(String urlIn, Session userSession) throws FailedActionRuntimeException {
        Element results = null;
        try {
            URL url = new URL(urlIn);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(1000);
            String cookie = (String) userSession.getAttribute("usercookie.object");
            if (cookie != null)
                conn.setRequestProperty("Cookie", cookie);
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            try {
                results = Xml.loadStream(in);
            } finally {
                in.close();
            }
        } catch (RuntimeException e) {
            throw new FailedActionRuntimeException(FailedActionReason.SYSTEM_ERROR);
        }
        return results;
    }
}
