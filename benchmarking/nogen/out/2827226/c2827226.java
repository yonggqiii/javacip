class c2827226 {

    public InputSource resolveEntity(String publicId, String systemId) {
        JavaCIPUnknownScope.allowXMLCatalogPI = false;
        String resolved = JavaCIPUnknownScope.catalogResolver.getResolvedEntity(publicId, systemId);
        if (resolved == null && JavaCIPUnknownScope.piCatalogResolver != null) {
            resolved = JavaCIPUnknownScope.piCatalogResolver.getResolvedEntity(publicId, systemId);
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
                JavaCIPUnknownScope.catalogManager.debug.message(1, "Failed to create InputSource", resolved);
                return null;
            }
        } else {
            return null;
        }
    }
}
