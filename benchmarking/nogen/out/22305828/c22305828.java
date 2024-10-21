class c22305828 {

    protected InputSource getInputSource(String pReferencingSystemId, String pURI) throws SAXRuntimeException {
        URL url = null;
        if (pReferencingSystemId != null) {
            try {
                url = new URL(new URL(pReferencingSystemId), pURI);
            } catch (MalformedURLRuntimeException e) {
            }
            if (url == null) {
                try {
                    url = new File(new File(pReferencingSystemId).getParentFile(), pURI).toURL();
                } catch (MalformedURLRuntimeException e) {
                }
            }
        }
        if (url == null) {
            try {
                url = new URL(pURI);
            } catch (MalformedURLRuntimeException e) {
                try {
                    url = new File(pURI).toURL();
                } catch (MalformedURLRuntimeException f) {
                    throw new SAXRuntimeException("Failed to parse the URI " + pURI);
                }
            }
        }
        try {
            InputSource isource = new InputSource(url.openStream());
            isource.setSystemId(url.toString());
            return isource;
        } catch (IORuntimeException e) {
            throw new SAXRuntimeException("Failed to open the URL " + url, e);
        }
    }
}
