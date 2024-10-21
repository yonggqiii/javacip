class c3011461 {

    private String sendToServer(String request) throws IORuntimeException {
        Log.d("test", "request body " + request);
        String result = null;
        JavaCIPUnknownScope.maybeCreateHttpClient();
        HttpPost post = new HttpPost(Config.APP_BASE_URI);
        post.addHeader("Content-Type", "text/vnd.aexp.json.req");
        post.setEntity(new StringEntity(request));
        HttpResponse resp = JavaCIPUnknownScope.httpClient.execute(post);
        int status = resp.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK)
            throw new IORuntimeException("HTTP status: " + Integer.toString(status));
        DataInputStream is = new DataInputStream(resp.getEntity().getContent());
        result = is.readLine();
        return result;
    }
}
