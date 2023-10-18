class c9827345 {

    private static Properties getProperties(String propFilename) {
        Properties properties = new Properties();
        try {
            URL url = Loader.getResource(propFilename);
            properties.load(url.openStream());
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.debug("Cannot find SAML property file: " + propFilename);
            throw new RuntimeRuntimeException("SAMLIssuerFactory: Cannot load properties: " + propFilename);
        }
        return properties;
    }
}
