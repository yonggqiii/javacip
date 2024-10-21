class c15354003 {

    public void testAddCommandProcessor() throws RuntimeException {
        String host = "localhost";
        int port = JavaCIPUnknownScope.DEFAULT_PORT;
        URLConnection connection = null;
        URL url = new URL("http://" + host + ":" + port + "/nonexistant");
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "addCommandProcessor", new Object[] { "nonexistant", new DummyCommandProcessor() }, new String[] { "java.lang.String", "mx4j.tools.adaptor.http.HttpCommandProcessor" });
        connection = url.openConnection();
        JavaCIPUnknownScope.assertEquals(200, ((HttpURLConnection) connection).getResponseCode());
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "removeCommandProcessor", new Object[] { "nonexistant" }, new String[] { "java.lang.String" });
        connection = url.openConnection();
        JavaCIPUnknownScope.assertEquals(404, ((HttpURLConnection) connection).getResponseCode());
        JavaCIPUnknownScope.server.invoke(JavaCIPUnknownScope.name, "addCommandProcessor", new Object[] { "nonexistant", "test.mx4j.tools.adaptor.http.HttpAdaptorTest$DummyCommandProcessor" }, new String[] { "java.lang.String", "java.lang.String" });
        connection = url.openConnection();
        JavaCIPUnknownScope.assertEquals(200, ((HttpURLConnection) connection).getResponseCode());
    }
}
