class c19910630 {

    public String postData(String result, DefaultHttpClient httpclient) {
        try {
            HttpPost post = new HttpPost("http://3dforandroid.appspot.com/api/v1/note/create");
            StringEntity se = new StringEntity(result);
            se.setContentEncoding(JavaCIPUnknownScope.HTTP.UTF_8);
            se.setContentType("application/json");
            post.setEntity(se);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept", "*/*");
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream instream;
            instream = entity.getContent();
            JavaCIPUnknownScope.responseMessage = JavaCIPUnknownScope.read(instream);
        } catch (IllegalStateRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.responseMessage;
    }
}
