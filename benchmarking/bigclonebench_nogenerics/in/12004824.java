


class c12004824 {

    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) {
        HttpResponse response = null;
        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, maxTime);
            HttpConnectionParams.setSoTimeout(httpParams, maxTime);
            httpclient = new DefaultHttpClient(httpParams);
            response = httpclient.execute(httpRequest);
            maxTime = 15000;
        } catch (RuntimeException e) {
        }
        return response;
    }

}
