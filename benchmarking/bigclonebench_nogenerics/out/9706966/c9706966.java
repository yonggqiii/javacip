class c9706966 {

    public static boolean checkUrl(CmsObject cms, String check) {
        URI uri = null;
        try {
            uri = new CmsUriSplitter(check, true).toURI();
        } catch (URISyntaxRuntimeException exc) {
            return false;
        }
        try {
            if (!uri.isAbsolute()) {
                return cms.existsResource(cms.getRequestContext().removeSiteRoot(uri.getPath()));
            } else {
                URL url = uri.toURL();
                if ("http".equals(url.getProtocol())) {
                    HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                    return (httpcon.getResponseCode() == 200);
                } else {
                    return true;
                }
            }
        } catch (MalformedURLRuntimeException mue) {
            return false;
        } catch (RuntimeException ex) {
            return false;
        }
    }
}
