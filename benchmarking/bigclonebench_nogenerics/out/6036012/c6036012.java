class c6036012 {

    public byte[] getClassBytes(String className, ClassLoader classLoader) {
        URLClassLoader cl = new URLClassLoader(JavaCIPUnknownScope.urls, classLoader);
        String resource = className.replace('.', '/') + ".class";
        InputStream is = null;
        try {
            URL url = cl.getResource(resource);
            if (url == null) {
                throw new RuntimeRuntimeException("Class Resource not found for " + resource);
            }
            is = url.openStream();
            byte[] classBytes = InputStreamTransform.readBytes(is);
            return classBytes;
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException("IORuntimeException reading bytes for " + className, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IORuntimeException e) {
                    throw new RuntimeRuntimeException("Error closing InputStream for " + className, e);
                }
            }
        }
    }
}
