class c21609544 {

    public static JSONObject delete(String uid) throws ClientProtocolRuntimeException, IORuntimeException, JSONRuntimeException {
        HttpClient client = new DefaultHttpClient(JavaCIPUnknownScope.params);
        HttpGet get = new HttpGet(JavaCIPUnknownScope.DELETE_URI + "?uid=" + uid);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            String res = EntityUtils.toString(response.getEntity());
            return new JSONObject(res);
        }
        throw new IORuntimeException("bad http response:" + response.getStatusLine().getReasonPhrase());
    }
}
