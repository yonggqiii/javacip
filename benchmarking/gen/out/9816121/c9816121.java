class c9816121 {

    private Callable<Request> newRequestCall(final Request request) {
        return new CallableImpl<Request>(request);
    }

    public Request call(Request request) {
        InputStream is = null;
        try {
            URI uri = request.uri;
            if (JavaCIPUnknownScope.DEBUG)
                Log.d(JavaCIPUnknownScope.TAG, "Requesting: " + uri);
            HttpGet httpGet = new HttpGet(uri.toString());
            httpGet.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = JavaCIPUnknownScope.mHttpClient.execute(httpGet);
            HttpHeader[] contentTypeHeaders = response.getHeaders("Content-Type");
            String mimeType = contentTypeHeaders[0].getValue();
            if (JavaCIPUnknownScope.DEBUG)
                Log.d(JavaCIPUnknownScope.TAG, "mimeType:" + mimeType);
            if (mimeType.startsWith("image")) {
                HttpEntity entity = response.getEntity();
                is = JavaCIPUnknownScope.getUngzippedContent(entity);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                byte[] hash = request.hash;
                if (JavaCIPUnknownScope.mResourceCache.store(hash, bitmap)) {
                    JavaCIPUnknownScope.mCache.put(uri.toString(), new SoftReference<Bitmap>(bitmap));
                    if (JavaCIPUnknownScope.DEBUG)
                        Log.d(JavaCIPUnknownScope.TAG, "Request successful: " + uri);
                } else {
                    JavaCIPUnknownScope.mResourceCache.invalidate(hash);
                }
            }
        } catch (IOException e) {
            if (JavaCIPUnknownScope.DEBUG)
                Log.d(JavaCIPUnknownScope.TAG, "IOException", e);
        } finally {
            if (JavaCIPUnknownScope.DEBUG)
                Log.e(JavaCIPUnknownScope.TAG, "Request finished: " + request.uri);
            JavaCIPUnknownScope.mActiveRequestsMap.remove(request);
            if (is != null) {
                JavaCIPUnknownScope.notifyObservers(request.uri);
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                if (JavaCIPUnknownScope.DEBUG)
                    e.printStackTrace();
            }
        }
        return request;
    }
}
