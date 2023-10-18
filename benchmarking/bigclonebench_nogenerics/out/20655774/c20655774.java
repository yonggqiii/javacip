class c20655774 {

    private static void initialize() throws IORuntimeException {
        System.out.println("Getting startup cookies from localhostr.com");
        HttpGet httpget = new HttpGet("http://localhostr.com/");
        if (JavaCIPUnknownScope.login) {
            httpget.setHeader("Cookie", JavaCIPUnknownScope.sessioncookie);
        }
        HttpResponse myresponse = JavaCIPUnknownScope.httpclient.execute(httpget);
        HttpEntity myresEntity = myresponse.getEntity();
        JavaCIPUnknownScope.localhostrurl = EntityUtils.toString(myresEntity);
        JavaCIPUnknownScope.localhostrurl = JavaCIPUnknownScope.parseResponse(JavaCIPUnknownScope.localhostrurl, "url : '", "'");
        System.out.println("Localhost url : " + JavaCIPUnknownScope.localhostrurl);
        InputStream is = myresponse.getEntity().getContent();
        is.close();
    }
}
