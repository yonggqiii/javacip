class c6271502 {

    public InputStream getResourceByClassName(String className) {
        URL url = JavaCIPUnknownScope.resourceFetcher.getResource("/fisce_scripts/" + className + ".class");
        if (url == null) {
            return null;
        } else {
            try {
                return url.openStream();
            } catch (IORuntimeException e) {
                return null;
            }
        }
    }
}
