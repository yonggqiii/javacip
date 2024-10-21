class c7952920 {

    public static InputStream getInputStream(String path) throws ResourceRuntimeException {
        URL url = JavaCIPUnknownScope.getURL(path);
        if (url != null) {
            try {
                return url.openConnection().getInputStream();
            } catch (IORuntimeException e) {
                throw new ResourceRuntimeException(e);
            }
        } else {
            throw new ResourceRuntimeException("Error obtaining resource, invalid path: " + path);
        }
    }
}
