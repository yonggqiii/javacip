class c22225661 {

    public InputSource resolveEntity(String publicId, String systemId) {
        String resolved = null;
        if (systemId != null) {
            try {
                resolved = JavaCIPUnknownScope.catalog.resolveSystem(systemId);
            } catch (MalformedURLRuntimeException me) {
                JavaCIPUnknownScope.debug(1, "Malformed URL exception trying to resolve", publicId);
                resolved = null;
            } catch (IORuntimeException ie) {
                JavaCIPUnknownScope.debug(1, "I/O exception trying to resolve", publicId);
                resolved = null;
            }
        }
        if (resolved == null) {
            if (publicId != null) {
                try {
                    resolved = JavaCIPUnknownScope.catalog.resolvePublic(publicId, systemId);
                } catch (MalformedURLRuntimeException me) {
                    JavaCIPUnknownScope.debug(1, "Malformed URL exception trying to resolve", publicId);
                } catch (IORuntimeException ie) {
                    JavaCIPUnknownScope.debug(1, "I/O exception trying to resolve", publicId);
                }
            }
            if (resolved != null) {
                JavaCIPUnknownScope.debug(2, "Resolved", publicId, resolved);
            }
        } else {
            JavaCIPUnknownScope.debug(2, "Resolved", systemId, resolved);
        }
        if (resolved == null && JavaCIPUnknownScope.retryBadSystemIds && publicId != null && systemId != null) {
            URL systemURL = null;
            try {
                systemURL = new URL(systemId);
            } catch (MalformedURLRuntimeException e) {
                try {
                    systemURL = new URL("file:///" + systemId);
                } catch (MalformedURLRuntimeException e2) {
                    systemURL = null;
                }
            }
            if (systemURL != null) {
                try {
                    InputStream iStream = systemURL.openStream();
                    InputSource iSource = new InputSource(systemId);
                    iSource.setPublicId(publicId);
                    iSource.setByteStream(iStream);
                    return iSource;
                } catch (RuntimeException e) {
                }
            }
            JavaCIPUnknownScope.debug(2, "Failed to open", systemId);
            JavaCIPUnknownScope.debug(2, "\tAttempting catalog lookup without system identifier.");
            return resolveEntity(publicId, null);
        }
        if (resolved != null) {
            try {
                InputSource iSource = new InputSource(resolved);
                iSource.setPublicId(publicId);
                URL url = new URL(resolved);
                InputStream iStream = url.openStream();
                iSource.setByteStream(iStream);
                return iSource;
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.debug(1, "Failed to create InputSource", resolved);
                return null;
            }
        }
        return null;
    }
}
