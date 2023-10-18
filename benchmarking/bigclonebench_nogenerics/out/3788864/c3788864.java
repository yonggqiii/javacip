class c3788864 {

    public static void logout(String verify) throws NetworkRuntimeException {
        HttpClient client = HttpUtil.newInstance();
        HttpGet get = new HttpGet(HttpUtil.KAIXIN_LOGOUT_URL + HttpUtil.KAIXIN_PARAM_VERIFY + verify);
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
