


class c3024985 {

    @Test
    public void testCopy_readerToOutputStream_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        try {
            IOUtils.copy((Reader) null, out);
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
