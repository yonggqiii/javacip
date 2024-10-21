class c20150446 {

    public String accessURL_Post(String url, String postStr, String encoding) throws Throwable {
        StringEntity entity = new StringEntity(postStr, encoding);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        HttpResponse response = JavaCIPUnknownScope.client.execute(httpPost);
        String entuity = EntityUtils.toString(response.getEntity());
        JavaCIPUnknownScope.handleHeaders(response.getAllHeaders());
        return entuity;
    }
}
