


class c3024988 {

    @Test
    public void testCopy_readerToOutputStream_Encoding_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        try {
            IOUtils.copy((Reader) null, out, "UTF16");
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
