


class c1508163 {

    public boolean sendMail(MailObject mail, boolean backup) throws NetworkRuntimeException, ContentRuntimeException {
        HttpClient client = HttpConfig.newInstance();
        HttpPost post = new HttpPost(HttpConfig.bbsURL() + HttpConfig.BBS_MAIL_SEND);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair(HttpConfig.BBS_MAIL_SEND_REF_PARAM_NAME, "pstmail"));
        nvps.add(new BasicNameValuePair(HttpConfig.BBS_MAIL_SEND_RECV_PARAM_NAME, mail.getSender()));
        nvps.add(new BasicNameValuePair(HttpConfig.BBS_MAIL_SEND_TITLE_PARAM_NAME, mail.getTitle()));
        nvps.add(new BasicNameValuePair(HttpConfig.BBS_MAIL_SEND_CONTENT_PARAM_NAME, mail.getContent()));
        if (backup) nvps.add(new BasicNameValuePair(HttpConfig.BBS_MAIL_SEND_BACKUP_PARAM_NAME, "backup"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, BBSBodyParseHelper.BBS_CHARSET));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (HTTPUtil.isHttp200(response)) {
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
