


class c3024989 {

    @Test
    public void testCopy_readerToOutputStream_Encoding_nullOut() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        try {
            IOUtils.copy(reader, (OutputStream) null, "UTF16");
            fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }

}
