class c3024986 {

    public void testCopy_readerToOutputStream_nullOut() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        try {
            IOUtils.copy(reader, (OutputStream) null);
            JavaCIPUnknownScope.fail();
        } catch (NullPointerRuntimeException ex) {
        }
    }
}
