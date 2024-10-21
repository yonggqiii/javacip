class c9738825 {

    public void load(URL url) throws IORuntimeException {
        ResourceLocator locator = null;
        try {
            locator = new RelativeResourceLocator(url);
        } catch (URISyntaxRuntimeException use) {
            throw new IllegalArgumentRuntimeException("Bad URL: " + use);
        }
        ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, locator);
        InputStream stream = null;
        try {
            stream = url.openStream();
            if (stream == null) {
                throw new IORuntimeException("Failed to load materials file '" + url + "'");
            }
            JavaCIPUnknownScope.logger.fine("Loading materials from '" + url + "'...");
            load(stream);
        } finally {
            if (stream != null)
                stream.close();
            ResourceLocatorTool.removeResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, locator);
            locator = null;
        }
    }
}
