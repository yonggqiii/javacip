class c960758 {

    public byte[] getBytesMethod(String url) {
        JavaCIPUnknownScope.logger.info("Facebook: @executing facebookGetMethod():" + url);
        byte[] responseBytes = null;
        try {
            HttpGet loginGet = new HttpGet(url);
            loginGet.addHeader("Accept-Encoding", "gzip");
            HttpResponse response = JavaCIPUnknownScope.httpClient.execute(loginGet);
            HttpEntity entity = response.getEntity();
            JavaCIPUnknownScope.logger.trace("Facebook: getBytesMethod: " + response.getStatusLine());
            if (entity != null) {
                InputStream in = response.getEntity().getContent();
                if (response.getEntity().getContentEncoding().getValue().equals("gzip")) {
                    in = new GZIPInputStream(in);
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                int n;
                while ((n = in.read(b)) != -1) {
                    out.write(b, 0, n);
                }
                responseBytes = out.toByteArray();
                in.close();
                entity.consumeContent();
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                JavaCIPUnknownScope.logger.warn("Facebook: Error Occured! Status Code = " + statusCode);
                responseBytes = null;
            }
            JavaCIPUnknownScope.logger.info("Facebook: Get Bytes Method done(" + statusCode + "), response bytes length: " + (responseBytes == null ? 0 : responseBytes.length));
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.warn("Facebook: ", e);
        }
        return responseBytes;
    }
}
