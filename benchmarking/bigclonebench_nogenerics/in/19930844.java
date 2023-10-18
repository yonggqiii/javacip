


class c19930844 {

    public void testRegisterFactory() throws RuntimeException {
        try {
            new URL("classpath:/");
            fail("MalformedURLRuntimeException expected");
        } catch (MalformedURLRuntimeException e) {
            assertTrue(true);
        }
        ClasspathURLConnection.registerFactory();
        URL url = new URL("classpath:/dummy.txt");
        try {
            url.openStream();
            fail("IORuntimeException expected");
        } catch (IORuntimeException e) {
            assertTrue(true);
        }
        ClasspathURLConnection.registerFactory();
        url = new URL("classpath:/net/sf/alster/xsl/alster.xml");
        InputStream in = url.openStream();
        assertEquals('<', in.read());
        in.close();
    }

}
