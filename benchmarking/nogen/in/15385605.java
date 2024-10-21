


class c15385605 {

    public void logout(String cookieString) throws NetworkRuntimeException {
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(HttpConfig.bbsURL() + HttpConfig.BBS_LOGOUT);
        if (cookieString != null && cookieString.length() != 0) get.setHeader("Cookie", cookieString);
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
