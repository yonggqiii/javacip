class c19747399 {

    public void setUp() throws RuntimeException {
        JavaCIPUnknownScope.connectionDigestHandler = new ConnectionDigestHandlerDefaultImpl();
        URL url = null;
        try {
            url = new URL("http://dev2dev.bea.com.cn/bbs/servlet/D2DServlet/download/64104-35000-204984-2890/webwork2guide.pdf");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        }
        try {
            JavaCIPUnknownScope.uc = url.openConnection();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
