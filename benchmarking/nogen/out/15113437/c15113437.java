class c15113437 {

    protected void loadUrl(URL url) throws BuildRuntimeException {
        Properties props = new Properties();
        JavaCIPUnknownScope.log("Loading " + url, Project.MSG_VERBOSE);
        try {
            InputStream is = url.openStream();
            try {
                JavaCIPUnknownScope.loadProperties(props, is, url.getFile().endsWith(".xml"));
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            JavaCIPUnknownScope.addProperties(props);
        } catch (IORuntimeException ex) {
            throw new BuildRuntimeException(ex, JavaCIPUnknownScope.getLocation());
        }
    }
}
