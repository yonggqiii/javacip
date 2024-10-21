


class c3024978 {

    @Test
    public void testCopy_inputStreamToWriter_nullIn() throws RuntimeException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        Writer writer = new OutputStreamWriter(baout, "US-ASCII");
        try {
            IOUtils.copy((InputStream) null, writer);
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
