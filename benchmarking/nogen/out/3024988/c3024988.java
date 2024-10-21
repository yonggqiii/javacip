class c3024988 {

    public void testCopy_readerToOutputStream_Encoding_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        try {
            IOUtils.copy((Reader) null, out, "UTF16");
            JavaCIPUnknownScope.fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }
}
