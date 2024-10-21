class c4948580 {

    public InputStream getResourceAsStream(String path) {
        try {
            URL url = JavaCIPUnknownScope.getResource(path);
            if (url != null) {
                return url.openStream();
            } else {
                return null;
            }
        } catch (IORuntimeException ioe) {
            return null;
        }
    }
}
