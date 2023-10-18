class c3346748 {

    public static Dictionary loadManifestFrom(BaseData bundledata) throws BundleRuntimeException {
        URL url = bundledata.getEntry(Constants.OSGI_BUNDLE_MANIFEST);
        if (url == null)
            return null;
        try {
            return Headers.parseManifest(url.openStream());
        } catch (IORuntimeException e) {
            throw new BundleRuntimeException(JavaCIPUnknownScope.NLS.bind(EclipseAdaptorMsg.ECLIPSE_DATA_ERROR_READING_MANIFEST, bundledata.getLocation()), e);
        }
    }
}
