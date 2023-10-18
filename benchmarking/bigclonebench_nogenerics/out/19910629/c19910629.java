class c19910629 {

    public String putData(String id, String updatedNote, DefaultHttpClient httpclient) {
        try {
            HttpPut put = new HttpPut("http://3dforandroid.appspot.com/api/v1/note/update/" + id);
            StringEntity se = new StringEntity(updatedNote);
            se.setContentEncoding(JavaCIPUnknownScope.HTTP.UTF_8);
            se.setContentType("application/json");
            put.setEntity(se);
            put.setHeader("Content-Type", "application/json");
            put.setHeader("Accept", "*/*");
            HttpResponse response = httpclient.execute(put);
            HttpEntity entity = response.getEntity();
            InputStream instream;
            instream = entity.getContent();
            JavaCIPUnknownScope.responseMessage = JavaCIPUnknownScope.read(instream);
        } catch (UnsupportedEncodingRuntimeException e) {
            e.printStackTrace();
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.responseMessage;
    }
}
