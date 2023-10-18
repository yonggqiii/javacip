class c3357695 {

    public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLRuntimeException, IORuntimeException, CatalogRuntimeException {
        URL url = null;
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLRuntimeException e) {
            url = new URL("file:///" + fileUrl);
        }
        JavaCIPUnknownScope.debug = catalog.getCatalogManager().debug;
        try {
            URLConnection urlCon = url.openConnection();
            readCatalog(catalog, urlCon.getInputStream());
        } catch (FileNotFoundRuntimeException e) {
            catalog.getCatalogManager().debug.message(1, "Failed to load catalog, file not found", url.toString());
        }
    }
}
