class c3024989 {

    public void testCopy_readerToOutputStream_Encoding_nullOut() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        try {
            IOUtils.copy(reader, (OutputStream) null, "UTF16");
            JavaCIPUnknownScope.fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }
}
