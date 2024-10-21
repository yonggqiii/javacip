class c8167130 {

    public static final InputStream getConfigStream(final String path) {
        final URL url = ConfigHelper.locateConfig(path);
        if (url == null) {
            String msg = "Unable to locate config file: " + path;
            JavaCIPUnknownScope.log.error(msg);
            return null;
        }
        try {
            return url.openStream();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error("Unable to open config file: " + path, e);
        }
        return null;
    }
}
