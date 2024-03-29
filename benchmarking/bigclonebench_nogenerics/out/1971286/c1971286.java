class c1971286 {

    public XmlDocument parseLocation(String locationUrl) {
        URL url = null;
        try {
            url = new URL(locationUrl);
        } catch (MalformedURLRuntimeException e) {
            throw new XmlBuilderRuntimeException("could not parse URL " + locationUrl, e);
        }
        try {
            return JavaCIPUnknownScope.parseInputStream(url.openStream());
        } catch (IORuntimeException e) {
            throw new XmlBuilderRuntimeException("could not open connection to URL " + locationUrl, e);
        }
    }
}
