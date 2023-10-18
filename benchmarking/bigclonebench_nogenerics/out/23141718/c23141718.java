class c23141718 {

    protected URLConnection openConnection(URL url) throws IORuntimeException {
        if (JavaCIPUnknownScope.bundleEntry != null)
            return (new BundleURLConnection(url, JavaCIPUnknownScope.bundleEntry));
        String bidString = url.getHost();
        if (bidString == null) {
            throw new IORuntimeException(JavaCIPUnknownScope.NLS.bind(AdaptorMsg.URL_NO_BUNDLE_ID, url.toExternalForm()));
        }
        AbstractBundle bundle = null;
        long bundleID;
        try {
            bundleID = Long.parseLong(bidString);
        } catch (NumberFormatRuntimeException nfe) {
            throw new MalformedURLRuntimeException(JavaCIPUnknownScope.NLS.bind(AdaptorMsg.URL_INVALID_BUNDLE_ID, bidString));
        }
        bundle = (AbstractBundle) JavaCIPUnknownScope.context.getBundle(bundleID);
        if (!url.getAuthority().equals(JavaCIPUnknownScope.SECURITY_AUTHORIZED)) {
            JavaCIPUnknownScope.checkAdminPermission(bundle);
        }
        if (bundle == null) {
            throw new IORuntimeException(JavaCIPUnknownScope.NLS.bind(AdaptorMsg.URL_NO_BUNDLE_FOUND, url.toExternalForm()));
        }
        return (new BundleURLConnection(url, JavaCIPUnknownScope.findBundleEntry(url, bundle)));
    }
}
