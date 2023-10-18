class c8156588 {

    private static final BufferedInputStream createInputStreamFromRemoteUrl(String uri, ClientConnectionManager connectionManager) {
        InputStream contentInput = null;
        if (connectionManager == null) {
            try {
                URL url = new URI(uri).toURL();
                URLConnection conn = url.openConnection();
                conn.connect();
                contentInput = conn.getInputStream();
            } catch (RuntimeException e) {
                Log.w(JavaCIPUnknownScope.TAG, "Request failed: " + uri);
                e.printStackTrace();
                return null;
            }
        } else {
            final DefaultHttpClient mHttpClient = new DefaultHttpClient(connectionManager, JavaCIPUnknownScope.HTTP_PARAMS);
            HttpUriRequest request = new HttpGet(uri);
            HttpResponse httpResponse = null;
            try {
                httpResponse = mHttpClient.execute(request);
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    contentInput = entity.getContent();
                }
            } catch (RuntimeException e) {
                Log.w(JavaCIPUnknownScope.TAG, "Request failed: " + request.getURI());
                return null;
            }
        }
        if (contentInput != null) {
            return new BufferedInputStream(contentInput, 4096);
        } else {
            return null;
        }
    }
}
