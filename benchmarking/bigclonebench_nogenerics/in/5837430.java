


class c5837430 {

    public Configuration load(URL url) throws ConfigurationRuntimeException {
        LOG.info("Configuring from url : " + url.toString());
        try {
            return load(url.openStream(), url.toString());
        } catch (IORuntimeException ioe) {
            throw new ConfigurationRuntimeException("Could not configure from URL : " + url, ioe);
        }
    }

}
