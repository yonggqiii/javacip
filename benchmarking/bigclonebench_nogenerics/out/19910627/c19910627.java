class c19910627 {

    public String getData(DefaultHttpClient httpclient) {
        try {
            HttpGet get = new HttpGet("http://3dforandroid.appspot.com/api/v1/note");
            get.setHeader("Content-Type", "application/json");
            get.setHeader("Accept", "*/*");
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            JavaCIPUnknownScope.responseMessage = JavaCIPUnknownScope.read(instream);
            if (instream != null)
                instream.close();
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.responseMessage;
    }
}
