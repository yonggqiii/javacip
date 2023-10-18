class c13631832 {

    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
        if (JavaCIPUnknownScope.logger.isLoggable(Level.FINE)) {
            JavaCIPUnknownScope.logger.fine("Try to resolve the resource with the public ID: " + publicId + ", system ID: " + systemId + " and baseURI " + baseURI + ".");
        }
        InputSource inputSource = null;
        try {
            inputSource = JavaCIPUnknownScope.resolveIntern(publicId, systemId);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.log(Level.SEVERE, "", e);
        }
        if (inputSource != null) {
            return new LSInputSAXWrapper(inputSource);
        }
        if (baseURI != null) {
            String resolved = baseURI.substring(0, baseURI.lastIndexOf('/') + 1) + systemId;
            try {
                URL url = new URL(resolved);
                url.openConnection().connect();
                if (JavaCIPUnknownScope.logger.isLoggable(Level.FINE)) {
                    JavaCIPUnknownScope.logger.fine("Resolve with help of baseURI to: " + resolved);
                }
                inputSource = new InputSource(resolved);
                return new LSInputSAXWrapper(inputSource);
            } catch (MalformedURLRuntimeException e) {
            } catch (IORuntimeException e) {
            }
        }
        if (JavaCIPUnknownScope.logger.isLoggable(Level.WARNING)) {
            JavaCIPUnknownScope.logger.warning("Failed to resolve the resource with the public ID: " + publicId + ", system ID: " + systemId + " and baseURI " + baseURI + ".");
        }
        return null;
    }
}
