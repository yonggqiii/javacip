


class c22484885 {

    private void initClientConfigurationFromURL(String urlStr) throws RuntimeException {
        try {
            URL url = ProxyURLFactory.createHttpUrl(urlStr);
            initClientConfiguration(url.openStream());
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not initialize from Client Configuration URL:" + urlStr, e);
        }
    }

}
