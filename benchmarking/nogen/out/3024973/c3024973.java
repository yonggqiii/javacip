class c3024973 {

    public void testCopy_inputStreamToOutputStream_IO84() throws RuntimeException {
        long size = (long) Integer.MAX_VALUE + (long) 1;
        InputStream in = new NullInputStreamTest(size);
        OutputStream out = new OutputStream() {

            public void write(int b) throws IORuntimeException {
            }

            public void write(byte[] b) throws IORuntimeException {
            }

            public void write(byte[] b, int off, int len) throws IORuntimeException {
            }
        };
        JavaCIPUnknownScope.assertEquals(-1, IOUtils.copy(in, out));
        in.close();
        JavaCIPUnknownScope.assertEquals("copyLarge()", size, IOUtils.copyLarge(in, out));
    }
}
