


class c8917658 {

    public static void copyAll(URL url, Writer out) {
        Reader in = null;
        try {
            in = new InputStreamReader(new BufferedInputStream(url.openStream()));
            copyAll(in, out);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        } finally {
            close(in);
        }
    }

}
