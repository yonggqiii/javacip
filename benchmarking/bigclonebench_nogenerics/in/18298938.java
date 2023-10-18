


class c18298938 {

    public void loginOAuth() throws OAuthMessageSignerRuntimeException, OAuthExpectationFailedRuntimeException, OAuthCommunicationRuntimeException, ClientProtocolRuntimeException, IORuntimeException, IllegalStateRuntimeException, SAXRuntimeException, ParserConfigurationRuntimeException, FactoryConfigurationError, AndroidRuntimeException {
        String url = getAuthentificationURL();
        HttpGet reqLogin = new HttpGet(url);
        consumer = new CommonsHttpOAuthConsumer(getConsumerKey(), getConsumerSecret());
        consumer.sign(reqLogin);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse resLogin = httpClient.execute(reqLogin);
        if (resLogin.getEntity() == null) {
            throw new AuthRemoteRuntimeException();
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resLogin.getEntity().getContent());
        Element eOAuthToken = (Element) document.getElementsByTagName("oauth_token").item(0);
        if (eOAuthToken == null) {
            throw new AuthRemoteRuntimeException();
        }
        Node e = eOAuthToken.getFirstChild();
        String sOAuthToken = e.getNodeValue();
        System.out.println("token: " + sOAuthToken);
        Element eOAuthTokenSecret = (Element) document.getElementsByTagName("oauth_token_secret").item(0);
        if (eOAuthTokenSecret == null) {
            throw new AuthRemoteRuntimeException();
        }
        e = eOAuthTokenSecret.getFirstChild();
        String sOAuthTokenSecret = e.getNodeValue();
        System.out.println("Secret: " + sOAuthTokenSecret);
        consumer.setTokenWithSecret(sOAuthToken, sOAuthTokenSecret);
    }

}
