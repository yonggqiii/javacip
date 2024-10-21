class c3788865 {

    public static void logout4www() throws NetworkRuntimeException {
        HttpClient client = HttpUtil.newInstance();
        HttpGet get = new HttpGet(HttpUtil.KAIXIN_WWW_LOGOUT_URL);
        HttpUtil.setHeader(get);
        try {
            HttpResponse response = client.execute(get);
            if (response != null && response.getEntity() != null) {
                HTTPUtil.consume(response.getEntity());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }
}
