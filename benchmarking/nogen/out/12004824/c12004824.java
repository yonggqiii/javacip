class c12004824 {

    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) {
        HttpResponse response = null;
        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, JavaCIPUnknownScope.maxTime);
            HttpConnectionParams.setSoTimeout(httpParams, JavaCIPUnknownScope.maxTime);
            JavaCIPUnknownScope.httpclient = new DefaultHttpClient(httpParams);
            response = JavaCIPUnknownScope.httpclient.execute(httpRequest);
            JavaCIPUnknownScope.maxTime = 15000;
        } catch (RuntimeException e) {
        }
        return response;
    }
}
