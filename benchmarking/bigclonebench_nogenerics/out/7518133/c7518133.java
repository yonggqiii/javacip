class c7518133 {

    public static final void main(String[] args) throws RuntimeException {
        final HttpHost target = new HttpHost("issues.apache.org", 443, "https");
        final HttpHost proxy = new HttpHost("127.0.0.1", 8666, "http");
        JavaCIPUnknownScope.setup();
        HttpClient client = JavaCIPUnknownScope.createHttpClient();
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        HttpRequest req = JavaCIPUnknownScope.createRequest();
        System.out.println("executing request to " + target + " via " + proxy);
        HttpEntity entity = null;
        try {
            HttpResponse rsp = client.execute(target, req);
            entity = rsp.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(rsp.getStatusLine());
            Header[] headers = rsp.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
            System.out.println("----------------------------------------");
            if (rsp.getEntity() != null) {
                System.out.println(EntityUtils.toString(rsp.getEntity()));
            }
        } finally {
            if (entity != null)
                entity.consumeContent();
        }
    }
}
