


class c3024993 {

    @Test
    public void testCopy_readerToWriter_nullOut() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        try {
            IOUtils.copy(reader, (Writer) null);
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
