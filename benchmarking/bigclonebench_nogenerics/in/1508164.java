


class c1508164 {

    public boolean delMail(MailObject mail) throws NetworkRuntimeException, ContentRuntimeException {
        HttpClient client = HttpConfig.newInstance();
        HttpGet get = new HttpGet(HttpConfig.bbsURL() + HttpConfig.BBS_MAIL_DEL + mail.getId());
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (HTTPUtil.isXmlContentType(response)) {
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
