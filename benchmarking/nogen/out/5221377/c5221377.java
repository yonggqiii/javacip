class c5221377 {

    public File unpackResource(String resourceName) throws IORuntimeException {
        File file = null;
        URL url = JavaCIPUnknownScope.getURL("/" + resourceName);
        URLConnection urlConn = url.openConnection();
        if (urlConn instanceof JarURLConnection) {
            file = JavaCIPUnknownScope.handleJarFile((JarURLConnection) urlConn, resourceName);
        } else if (urlConn instanceof BundleURLConnection) {
            file = JavaCIPUnknownScope.handleBundleFile((BundleURLConnection) urlConn);
        } else {
            file = JavaCIPUnknownScope.handleClassicFile(resourceName);
        }
        return file;
    }
}
