class c5505848 {

    public void testResponseTimeout() throws RuntimeException {
        JavaCIPUnknownScope.server.enqueue(new MockResponse().setBody("ABC").clearHeaders().addHeader("Content-Length: 4"));
        JavaCIPUnknownScope.server.enqueue(new MockResponse().setBody("DEF"));
        JavaCIPUnknownScope.server.play();
        URLConnection urlConnection = JavaCIPUnknownScope.server.getUrl("/").openConnection();
        urlConnection.setReadTimeout(1000);
        InputStream in = urlConnection.getInputStream();
        JavaCIPUnknownScope.assertEquals('A', in.read());
        JavaCIPUnknownScope.assertEquals('B', in.read());
        JavaCIPUnknownScope.assertEquals('C', in.read());
        try {
            in.read();
            JavaCIPUnknownScope.fail();
        } catch (SocketTimeoutRuntimeException expected) {
        }
        URLConnection urlConnection2 = JavaCIPUnknownScope.server.getUrl("/").openConnection();
        InputStream in2 = urlConnection2.getInputStream();
        JavaCIPUnknownScope.assertEquals('D', in2.read());
        JavaCIPUnknownScope.assertEquals('E', in2.read());
        JavaCIPUnknownScope.assertEquals('F', in2.read());
        JavaCIPUnknownScope.assertEquals(-1, in2.read());
        JavaCIPUnknownScope.assertEquals(0, JavaCIPUnknownScope.server.takeRequest().getSequenceNumber());
        JavaCIPUnknownScope.assertEquals(0, JavaCIPUnknownScope.server.takeRequest().getSequenceNumber());
    }
}
