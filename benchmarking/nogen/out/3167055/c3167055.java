class c3167055 {

    public void setUp() throws IORuntimeException {
        JavaCIPUnknownScope.testSbk = File.createTempFile("songbook", "sbk");
        IOUtils.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.sbk"), new FileOutputStream(JavaCIPUnknownScope.testSbk));
        JavaCIPUnknownScope.test1Sbk = File.createTempFile("songbook", "sbk");
        IOUtils.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("test1.sbk"), new FileOutputStream(JavaCIPUnknownScope.test1Sbk));
    }
}
