class c11970147 {

    public String httpToStringStupid(String url) throws IllegalStateRuntimeException, IORuntimeException, HttpRuntimeException, InterruptedRuntimeException, URISyntaxRuntimeException {
        JavaCIPUnknownScope.LOG.info("Loading URL: " + url);
        String pageDump = null;
        JavaCIPUnknownScope.getParams().setParameter(ClientPNames.COOKIE_POLICY, JavaCIPUnknownScope.org.apache.http.client.params.CookiePolicy.BROWSER_COMPATIBILITY);
        JavaCIPUnknownScope.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, JavaCIPUnknownScope.getSocketTimeout());
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, JavaCIPUnknownScope.getSocketTimeout());
        HttpResponse response = JavaCIPUnknownScope.execute(httpget);
        HttpEntity entity = response.getEntity();
        pageDump = IOUtils.toString(entity.getContent(), "UTF-8");
        return pageDump;
    }
}
