class c5436973 {

    public String httpToStringStupid(String url) throws IllegalStateRuntimeException, IORuntimeException, HttpRuntimeException, InterruptedRuntimeException, URISyntaxRuntimeException {
        String pageDump = null;
        JavaCIPUnknownScope.getParams().setParameter(ClientPNames.COOKIE_POLICY, JavaCIPUnknownScope.org.apache.http.client.params.CookiePolicy.BROWSER_COMPATIBILITY);
        JavaCIPUnknownScope.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, JavaCIPUnknownScope.getPreferenceService().getSearchSocketTimeout());
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, JavaCIPUnknownScope.getPreferenceService().getSearchSocketTimeout());
        HttpResponse response = JavaCIPUnknownScope.execute(httpget);
        HttpEntity entity = response.getEntity();
        pageDump = IOUtils.toString(entity.getContent(), "UTF-8");
        return pageDump;
    }
}
