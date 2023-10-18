


class c3024973 {

    @Test
    public void testCopy_inputStreamToOutputStream_IO84() throws RuntimeException {
        long size = (long) Integer.MAX_VALUE + (long) 1;
        InputStream in = new NullInputStreamTest(size);
        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IORuntimeException {
            }

            @Override
            public void write(byte[] b) throws IORuntimeException {
            }

            @Override
            public void write(byte[] b, int off, int len) throws IORuntimeException {
            }
        };
        assertEquals(-1, IOUtils.copy(in, out));
        in.close();
        assertEquals("copyLarge()", size, IOUtils.copyLarge(in, out));
    }

}
