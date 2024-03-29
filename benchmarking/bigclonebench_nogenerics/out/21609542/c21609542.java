class c21609542 {

    public static long[] getUids(String myUid) throws ClientProtocolRuntimeException, IORuntimeException, JSONRuntimeException {
        HttpClient client = new DefaultHttpClient(JavaCIPUnknownScope.params);
        HttpGet get = new HttpGet(JavaCIPUnknownScope.UIDS_URI);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            String res = EntityUtils.toString(response.getEntity());
            JSONArray result = new JSONArray(res);
            long[] friends = new long[result.length()];
            long uid = Long.parseLong(myUid);
            for (int i = 0; i < result.length(); i++) {
                if (uid != result.getLong(i)) {
                    friends[i] = result.getLong(i);
                }
            }
            return friends;
        }
        throw new IORuntimeException("bad http response:" + response.getStatusLine().getReasonPhrase());
    }
}
