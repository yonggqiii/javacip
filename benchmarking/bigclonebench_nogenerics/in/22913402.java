


class c22913402 {

    protected Properties load(URL url) {
        try {
            InputStream i = url.openStream();
            Properties p = new Properties();
            p.load(i);
            i.close();
            return p;
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
