


class c19910628 {

    public String deleteData(String id, DefaultHttpClient httpclient) {
        try {
            HttpDelete del = new HttpDelete("http://3dforandroid.appspot.com/api/v1/note/delete/" + id);
            del.setHeader("Content-Type", "application/json");
            del.setHeader("Accept", "*/*");
            HttpResponse response = httpclient.execute(del);
            HttpEntity entity = response.getEntity();
            InputStream instream;
            instream = entity.getContent();
            responseMessage = read(instream);
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

}
