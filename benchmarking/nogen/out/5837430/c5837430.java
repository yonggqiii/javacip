class c5837430 {

    public Configuration load(URL url) throws ConfigurationRuntimeException {
        JavaCIPUnknownScope.LOG.info("Configuring from url : " + url.toString());
        try {
            return JavaCIPUnknownScope.load(url.openStream(), url.toString());
        } catch (IORuntimeException ioe) {
            throw new ConfigurationRuntimeException("Could not configure from URL : " + url, ioe);
        }
    }
}
