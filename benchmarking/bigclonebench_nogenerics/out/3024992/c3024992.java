class c3024992 {

    public void testCopy_readerToWriter_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        Writer writer = new OutputStreamWriter(baout, "US-ASCII");
        try {
            IOUtils.copy((Reader) null, writer);
            JavaCIPUnknownScope.fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }
}
