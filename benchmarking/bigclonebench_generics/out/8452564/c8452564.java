class c8452564 {

    public void run() {
        try {
            HttpPost httpPostRequest = new HttpPost(Feesh.device_URL);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("c", "feed"));
            nameValuePairs.add(new BasicNameValuePair("amount", String.valueOf(JavaCIPUnknownScope.foodAmount)));
            nameValuePairs.add(new BasicNameValuePair("type", String.valueOf(JavaCIPUnknownScope.foodType)));
            httpPostRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = (HttpResponse) new DefaultHttpClient().execute(httpPostRequest);
            HttpEntity entity = httpResponse.getEntity();
            String resultString = "";
            if (entity != null) {
                InputStream instream = entity.getContent();
                resultString = JavaCIPUnknownScope.convertStreamToString(instream);
                instream.close();
            }
            Message msg_toast = new Message();
            msg_toast.obj = resultString;
            JavaCIPUnknownScope.toast_handler.sendMessage(msg_toast);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
