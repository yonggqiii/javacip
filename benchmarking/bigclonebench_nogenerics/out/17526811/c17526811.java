class c17526811 {

    private String getDocumentAsString(URL url) throws IORuntimeException {
        StringBuffer result = new StringBuffer();
        InputStream in = url.openStream();
        int c;
        while ((c = in.read()) != -1) result.append((char) c);
        return result.toString();
    }
}
