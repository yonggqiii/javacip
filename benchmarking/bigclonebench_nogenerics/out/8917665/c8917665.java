class c8917665 {

    public static void copyAll(URL url, StringBuilder ret) {
        Reader in = null;
        try {
            in = new InputStreamReader(new BufferedInputStream(url.openStream()));
            copyAll(in, ret);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        } finally {
            JavaCIPUnknownScope.close(in);
        }
    }
}
