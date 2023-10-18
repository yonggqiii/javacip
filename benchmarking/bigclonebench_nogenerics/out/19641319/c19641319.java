class c19641319 {

    public void testDoPost() throws RuntimeException {
        URL url = null;
        url = new URL("http://127.0.0.1:" + JavaCIPUnknownScope.connector.getLocalPort() + "/test/dump/info?query=foo");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.addRequestProperty(HttpHeaders.CONTENT_TYPE, MimeTypes.FORM_ENCODED);
        connection.addRequestProperty(HttpHeaders.CONTENT_LENGTH, "10");
        connection.getOutputStream().write("abcd=1234\n".getBytes());
        connection.getOutputStream().flush();
        connection.connect();
        String s0 = JavaCIPUnknownScope.IO.toString(connection.getInputStream());
        JavaCIPUnknownScope.assertTrue(s0.startsWith("<html>"));
        JavaCIPUnknownScope.assertTrue(s0.indexOf("<td>POST</td>") > 0);
        JavaCIPUnknownScope.assertTrue(s0.indexOf("abcd:&nbsp;</th><td>1234") > 0);
    }
}
