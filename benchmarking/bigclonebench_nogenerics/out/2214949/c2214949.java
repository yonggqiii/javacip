class c2214949 {

    private static int get(URL url, byte[] content) throws IORuntimeException {
        int len = -1;
        InputStream in = null;
        try {
            in = new BufferedInputStream(url.openStream());
            String type = URLConnection.guessContentTypeFromStream(in);
            if (type == null || type.compareTo("text/html") != 0) {
                return -1;
            }
            len = JavaCIPUnknownScope.read(in, content);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            JavaCIPUnknownScope.close(in);
        }
        return len;
    }
}
