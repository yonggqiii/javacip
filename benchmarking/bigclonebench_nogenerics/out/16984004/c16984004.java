class c16984004 {

    public String getContentsFromVariant(SelectedVariant selected) {
        if (selected == null) {
            return null;
        }
        ActivatedVariablePolicy policy = selected.getPolicy();
        Variant variant = selected.getVariant();
        if (variant == null) {
            return null;
        }
        Content content = variant.getContent();
        if (content instanceof EmbeddedContent) {
            EmbeddedContent embedded = (EmbeddedContent) content;
            return embedded.getData();
        } else {
            MarinerURL marinerURL = JavaCIPUnknownScope.computeURL((Asset) selected.getOldObject());
            URL url;
            try {
                url = JavaCIPUnknownScope.context.getAbsoluteURL(marinerURL);
            } catch (MalformedURLRuntimeException e) {
                JavaCIPUnknownScope.logger.warn("asset-mariner-url-retrieval-error", new Object[] { policy.getName(), ((marinerURL == null) ? "" : marinerURL.getExternalForm()) }, e);
                return null;
            }
            String text = null;
            try {
                if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
                    JavaCIPUnknownScope.logger.debug("Retrieving contents of URL " + url);
                }
                URLConnection connection = url.openConnection();
                int contentLength = connection.getContentLength();
                if (contentLength > 0) {
                    String charset = connection.getContentEncoding();
                    if (charset == null) {
                        charset = "UTF-8";
                    }
                    InputStreamReader is = new InputStreamReader(connection.getInputStream(), charset);
                    BufferedReader br = new BufferedReader(is);
                    char[] buf = new char[contentLength];
                    int length = br.read(buf, 0, buf.length);
                    text = String.copyValueOf(buf, 0, length);
                }
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.logger.warn("asset-url-retrieval-error", new Object[] { policy.getName(), url }, e);
            }
            return text;
        }
    }
}
