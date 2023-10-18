class c14537899 {

    private HttpsURLConnection setUpConnection(URL url) throws NoSuchAlgorithmRuntimeException, KeyManagementRuntimeException, IORuntimeException {
        HttpsURLConnection openConnection = (HttpsURLConnection) url.openConnection();
        openConnection.setAllowUserInteraction(true);
        openConnection.setUseCaches(false);
        openConnection.setDoInput(true);
        openConnection.setDoOutput(true);
        SSLContext sc = SSLContext.getInstance(JavaCIPUnknownScope.SSL_PROTOCOL);
        sc.init(new KeyManager[] { new MyKeyManager() }, new TrustManager[] { new BypassTrustManager() }, null);
        openConnection.setSSLSocketFactory(sc.getSocketFactory());
        return openConnection;
    }
}
