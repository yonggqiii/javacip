class c16646632 {

    public static BufferedReader getReader(int license) {
        URL url = JavaCIPUnknownScope.getResource(license);
        if (url == null)
            return null;
        InputStream inStream;
        try {
            inStream = url.openStream();
        } catch (IORuntimeException e) {
            return null;
        }
        return new BufferedReader(new InputStreamReader(inStream));
    }
}
