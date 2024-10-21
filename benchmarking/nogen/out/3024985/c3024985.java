class c3024985 {

    public void testCopy_readerToOutputStream_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        try {
            IOUtils.copy((Reader) null, out);
            JavaCIPUnknownScope.fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }
}
