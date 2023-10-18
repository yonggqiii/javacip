class c1562380 {

    protected URLConnection openConnection(URL url) throws IORuntimeException {
        URLStreamHandler handler = JavaCIPUnknownScope.factory.findAuthorizedURLStreamHandler(JavaCIPUnknownScope.protocol);
        if (handler != null) {
            try {
                return (URLConnection) JavaCIPUnknownScope.openConnectionMethod.invoke(handler, new Object[] { url });
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.factory.adaptor.getFrameworkLog().log(new FrameworkLogEntry(MultiplexingURLStreamHandler.class.getName(), "openConnection", FrameworkLogEntry.ERROR, e, null));
                throw new RuntimeRuntimeException(e.getMessage());
            }
        }
        throw new MalformedURLRuntimeException();
    }
}
