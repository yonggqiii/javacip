


class c3167055 {

    @Before
    public void setUp() throws IORuntimeException {
        testSbk = File.createTempFile("songbook", "sbk");
        IOUtils.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.sbk"), new FileOutputStream(testSbk));
        test1Sbk = File.createTempFile("songbook", "sbk");
        IOUtils.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("test1.sbk"), new FileOutputStream(test1Sbk));
    }

}
