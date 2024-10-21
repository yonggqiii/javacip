class c19845293 {

    public static Properties load(URL url) {
        if (url == null) {
            return new Properties();
        }
        InputStream in = null;
        try {
            in = url.openStream();
            Properties ret = new Properties();
            ret.load(in);
            return ret;
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.LOG.error("Error closing", e);
                }
            }
        }
    }
}
