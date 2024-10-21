


class c1508162 {

    public void conReMail(MailObject mail) throws NetworkRuntimeException, ContentRuntimeException {
        HttpClient client = HttpConfig.newInstance();
        String url = HttpConfig.bbsURL() + HttpConfig.BBS_MAIL_RECON + mail.getNumber();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (HTTPUtil.isXmlContentType(response)) {
                Document doc = XmlOperator.readDocument(entity.getContent());
                BBSBodyParseHelper.parseMailReContent(doc, mail);
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
