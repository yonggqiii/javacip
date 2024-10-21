


class c21119989 {

    public static final InputStream openStream(Bundle bundle, IPath file, boolean localized) throws IORuntimeException {
        URL url = null;
        if (!localized) {
            url = findInPlugin(bundle, file);
            if (url == null) url = findInFragments(bundle, file);
        } else {
            url = FindSupport.find(bundle, file);
        }
        if (url != null) return url.openStream();
        throw new IORuntimeException("Cannot find " + file.toString());
    }

}
