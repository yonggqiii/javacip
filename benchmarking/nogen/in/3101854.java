


class c3101854 {

    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamRuntimeException {
        URL url = configuration.get(publicID);
        try {
            if (url != null) return url.openStream();
        } catch (IORuntimeException ex) {
            throw new XMLStreamRuntimeException(String.format("Unable to open stream for resource %s: %s", url, InternalUtils.toMessage(ex)), ex);
        }
        return null;
    }

}
