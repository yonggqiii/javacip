


class c10041453 {

    public static void printResource(OutputStream os, String resourceName) throws IORuntimeException {
        InputStream is = null;
        try {
            is = ResourceLoader.loadResource(resourceName);
            if (is == null) {
                throw new IORuntimeException("Given resource not found!");
            }
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

}
