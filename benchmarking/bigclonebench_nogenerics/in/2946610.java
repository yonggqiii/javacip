


class c2946610 {

    private static Document getDocument(URL url, String applicationVersion, boolean addHeader, int timeOut) throws IORuntimeException, ParserConfigurationRuntimeException, SAXRuntimeException {
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setConnectTimeout(1000 * timeOut);
        huc.setRequestMethod("GET");
        if (addHeader) {
            huc.setRequestProperty("JavaPEG-Version", applicationVersion);
        }
        huc.connect();
        int code = huc.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new IORuntimeException("Invaild HTTP response: " + code);
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(huc.getInputStream());
    }

}
