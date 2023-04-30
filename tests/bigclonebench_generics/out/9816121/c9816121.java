class c9816121 {

    private Callable<Request> newRequestCall(final Request request) {
        return new Callable<Request>() {

            public Request call() {
                InputStream is = null;
                try {
                    if (JavaCIPUnknownScope.DEBUG)
                        Log.d(JavaCIPUnknownScope.TAG, "Requesting: " + request.uri);
                    HttpGet httpGet = new HttpGet(request.uri.toString());
                    httpGet.addHeader("Accept-Encoding", "gzip");
                    HttpResponse response = JavaCIPUnknownScope.mHttpClient.execute(httpGet);
                    String mimeType = response.getHeaders("Content-Type")[0].getValue();
                    if (JavaCIPUnknownScope.DEBUG)
                        Log.d(JavaCIPUnknownScope.TAG, "mimeType:" + mimeType);
                    if (mimeType.startsWith("image")) {
                        HttpEntity entity = response.getEntity();
                        is = JavaCIPUnknownScope.getUngzippedContent(entity);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        if (JavaCIPUnknownScope.mResourceCache.store(request.hash, bitmap)) {
                            JavaCIPUnknownScope.mCache.put(request.uri.toString(), new SoftReference<Bitmap>(bitmap));
                            if (JavaCIPUnknownScope.DEBUG)
                                Log.d(JavaCIPUnknownScope.TAG, "Request successful: " + request.uri);
                        } else {
                            JavaCIPUnknownScope.mResourceCache.invalidate(request.hash);
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
        };
    }
}
