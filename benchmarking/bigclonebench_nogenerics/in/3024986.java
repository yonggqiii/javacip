


class c3024986 {

    @Test
    public void testCopy_readerToOutputStream_nullOut() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        try {
            IOUtils.copy(reader, (OutputStream) null);
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
