class c15354002 {

    public void testAuthentication() throws RuntimeException {
        String host = "localhost";
        int port = JavaCIPUnknownScope.DEFAULT_PORT;
        URL url = new URL("http://" + host + ":" + port + "/");
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        in.close();
        JavaCIPUnknownScope.waitToStop();
        JavaCIPUnknownScope.server.setAttribute(JavaCIPUnknownScope.name, new Attribute("AuthenticationMethod", "basic"));
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "addAuthorization", new Object[] { "mx4j", "mx4j" }, new String[] { "java.lang.String", "java.lang.String" });
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "start", null, null);
        url = new URL("http://" + host + ":" + port + "/");
        connection = url.openConnection();
        try {
            in = connection.getInputStream();
        } catch (RuntimeException e) {
        } finally {
            in.close();
        }
        JavaCIPUnknownScope.assertEquals(((HttpURLConnection) connection).getResponseCode(), 401);
        url = new URL("http://" + host + ":" + port + "/");
        connection = url.openConnection();
        connection.setRequestProperty("Authorization", "basic bXg0ajpteDRq");
        in = connection.getInputStream();
        in.close();
        JavaCIPUnknownScope.waitToStop();
        JavaCIPUnknownScope.server.setAttribute(JavaCIPUnknownScope.name, new Attribute("AuthenticationMethod", "none"));
    }
}
