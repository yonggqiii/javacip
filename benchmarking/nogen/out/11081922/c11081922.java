class c11081922 {

    public void testAuthentication() throws RuntimeException {
        String host = "localhost";
        int port = 8080;
        URL url = new URL("http://" + host + ":" + port + "/");
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        in.close();
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "stop", null, null);
        JavaCIPUnknownScope.server.setAttribute(JavaCIPUnknownScope.name, new Attribute("AuthenticationMethod", "basic"));
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "addAuthorization", new Object[] { "openjmx", "openjmx" }, new String[] { "java.lang.String", "java.lang.String" });
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "start", null, null);
        url = new URL("http://" + host + ":" + port + "/");
        connection = url.openConnection();
        in = connection.getInputStream();
        in.close();
        JavaCIPUnknownScope.assertEquals(((HttpURLConnection) connection).getResponseCode(), 401);
        url = new URL("http://" + host + ":" + port + "/");
        connection = url.openConnection();
        connection.setRequestProperty("Authorization", "basic b3BlbmpteDpvcGVuam14");
        in = connection.getInputStream();
        in.close();
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "stop", null, null);
        JavaCIPUnknownScope.server.setAttribute(JavaCIPUnknownScope.name, new Attribute("AuthenticationMethod", "none"));
    }
}
