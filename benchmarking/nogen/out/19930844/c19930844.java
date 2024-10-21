class c19930844 {

    public void testRegisterFactory() throws RuntimeException {
        try {
            new URL("classpath:/");
            JavaCIPUnknownScope.fail("MalformedURLRuntimeException expected");
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.assertTrue(true);
        }
        ClasspathURLConnection.registerFactory();
        URL url = new URL("classpath:/dummy.txt");
        try {
            url.openStream();
            JavaCIPUnknownScope.fail("IORuntimeException expected");
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.assertTrue(true);
        }
        ClasspathURLConnection.registerFactory();
        url = new URL("classpath:/net/sf/alster/xsl/alster.xml");
        InputStream in = url.openStream();
        JavaCIPUnknownScope.assertEquals('<', in.read());
        in.close();
    }
}
