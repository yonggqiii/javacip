class c18809524 {

    public void send(String payload, TransportReceiver receiver) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost();
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("Cookie", JavaCIPUnknownScope.cookie);
        post.setURI(JavaCIPUnknownScope.uri);
        Throwable ex;
        try {
            post.setEntity(new StringEntity(payload, "UTF-8"));
            HttpResponse response = client.execute(post);
            if (200 == response.getStatusLine().getStatusCode()) {
                String contents = JavaCIPUnknownScope.readStreamAsString(response.getEntity().getContent());
                receiver.onTransportSuccess(contents);
            } else {
                receiver.onTransportFailure(new ServerFailure(response.getStatusLine().getReasonPhrase()));
            }
            return;
        } catch (UnsupportedEncodingRuntimeException e) {
            ex = e;
        } catch (ClientProtocolRuntimeException e) {
            ex = e;
        } catch (IORuntimeException e) {
            ex = e;
        }
        receiver.onTransportFailure(new ServerFailure(ex.getMessage()));
    }
}