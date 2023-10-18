class c20655772 {

    public static void main(String[] args) throws IORuntimeException {
        JavaCIPUnknownScope.httpclient = new DefaultHttpClient();
        JavaCIPUnknownScope.httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        JavaCIPUnknownScope.loginLocalhostr();
        JavaCIPUnknownScope.initialize();
        HttpOptions httpoptions = new HttpOptions(JavaCIPUnknownScope.localhostrurl);
        HttpResponse myresponse = JavaCIPUnknownScope.httpclient.execute(httpoptions);
        HttpEntity myresEntity = myresponse.getEntity();
        System.out.println(EntityUtils.toString(myresEntity));
        JavaCIPUnknownScope.fileUpload();
    }
}
