class c9033639 {

    public void testHttpsConnection() throws Throwable {
        JavaCIPUnknownScope.setUpStoreProperties();
        try {
            SSLContext ctx = JavaCIPUnknownScope.getContext();
            ServerSocket ss = ctx.getServerSocketFactory().createServerSocket(0);
            TestHostnameVerifier hnv = new TestHostnameVerifier();
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
            URL url = new URL("https://localhost:" + ss.getLocalPort());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            SSLSocket peerSocket = (SSLSocket) JavaCIPUnknownScope.doInteraction(connection, ss);
            JavaCIPUnknownScope.checkConnectionStateParameters(connection, peerSocket);
            connection.connect();
        } finally {
            JavaCIPUnknownScope.tearDownStoreProperties();
        }
    }
}
