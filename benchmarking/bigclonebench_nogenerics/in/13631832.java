


class c13631832 {

    @Nullable
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Try to resolve the resource with the public ID: " + publicId + ", system ID: " + systemId + " and baseURI " + baseURI + ".");
        }
        InputSource inputSource = null;
        try {
            inputSource = resolveIntern(publicId, systemId);
        } catch (IORuntimeException e) {
            logger.log(Level.SEVERE, "", e);
        }
        if (inputSource != null) {
            return new LSInputSAXWrapper(inputSource);
        }
        if (baseURI != null) {
            String resolved = baseURI.substring(0, baseURI.lastIndexOf('/') + 1) + systemId;
            try {
                URL url = new URL(resolved);
                url.openConnection().connect();
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Resolve with help of baseURI to: " + resolved);
                }
                inputSource = new InputSource(resolved);
                return new LSInputSAXWrapper(inputSource);
            } catch (MalformedURLRuntimeException e) {
            } catch (IORuntimeException e) {
            }
        }
        if (logger.isLoggable(Level.WARNING)) {
            logger.warning("Failed to resolve the resource with the public ID: " + publicId + ", system ID: " + systemId + " and baseURI " + baseURI + ".");
        }
        return null;
    }

}
