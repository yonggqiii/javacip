class c12417893 {

    public byte[] download(URI uri) throws NetworkRuntimeException {
        JavaCIPUnknownScope.log.info("download: " + uri);
        HttpGet httpGet = new HttpGet(uri.toString());
        try {
            HttpResponse httpResponse = JavaCIPUnknownScope.httpClient.execute(httpGet);
            return EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (IORuntimeException e) {
            throw new NetworkRuntimeException(e);
        } finally {
            httpGet.abort();
        }
    }
}
