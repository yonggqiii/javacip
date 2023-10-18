


class c3788863 {

    public static void home4www(String location) throws NetworkRuntimeException {
        HttpClient client = HttpUtil.newInstance();
        HttpGet get = new HttpGet(HttpUtil.KAIXIN_WWW_URL + location);
        HttpUtil.setHeader(get);
        try {
            HttpResponse response = client.execute(get);
            HTTPUtil.consume(response.getEntity());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }

}
