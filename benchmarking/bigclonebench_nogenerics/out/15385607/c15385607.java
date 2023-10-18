class c15385607 {

    public boolean addFavBoard(BoardObject board) throws NetworkRuntimeException, ContentRuntimeException {
        String url = HttpConfig.bbsURL() + HttpConfig.BBS_FAV_ADD + board.getId();
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (HTTPUtil.isHttp200(response) && HTTPUtil.isXmlContentType(response)) {
                HTTPUtil.consume(response.getEntity());
                return true;
            } else {
                String msg = BBSBodyParseHelper.parseFailMsg(entity);
                throw new ContentRuntimeException(msg);
            }
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        } catch (IORuntimeException e) {
            e.printStackTrace();
            throw new NetworkRuntimeException(e);
        }
    }
}
