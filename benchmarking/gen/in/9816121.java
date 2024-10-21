


class c9816121 {

    private Callable<Request> newRequestCall(final Request request) {
        return new CallableImpl<Request>(request);
    }

    public Request call(Request request) {
        InputStream is = null;
        try {
            URI uri = request.uri;
            if (DEBUG) Log.d(TAG, "Requesting: " + uri);
            HttpGet httpGet = new HttpGet(uri.toString());
            httpGet.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = mHttpClient.execute(httpGet);
            HttpHeader[] contentTypeHeaders = response.getHeaders("Content-Type");
            String mimeType = contentTypeHeaders[0].getValue();
            if (DEBUG) Log.d(TAG, "mimeType:" + mimeType);
            if (mimeType.startsWith("image")) {
                HttpEntity entity = response.getEntity();
                is = getUngzippedContent(entity);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                byte[] hash = request.hash;
                if (mResourceCache.store(hash, bitmap)) {
                    mCache.put(uri.toString(), new SoftReference<Bitmap>(bitmap));
                    if (DEBUG) Log.d(TAG, "Request successful: " + uri);
                } else {
                    mResourceCache.invalidate(hash);
                }
            }
        } catch (IOException e) {
            if (DEBUG) Log.d(TAG, "IOException", e);
        } finally {
            if (DEBUG) Log.e(TAG, "Request finished: " + request.uri);
            mActiveRequestsMap.remove(request);
            if (is != null) {
                notifyObservers(request.uri);
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                if (DEBUG) e.printStackTrace();
            }
        }
        return request;
    }
}
