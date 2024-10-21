


class c12417893 {

    @Override
    public byte[] download(URI uri) throws NetworkRuntimeException {
        log.info("download: " + uri);
        HttpGet httpGet = new HttpGet(uri.toString());
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return EntityUtils.toByteArray(httpResponse.getEntity());
        } catch (IORuntimeException e) {
            throw new NetworkRuntimeException(e);
        } finally {
            httpGet.abort();
        }
    }

}
