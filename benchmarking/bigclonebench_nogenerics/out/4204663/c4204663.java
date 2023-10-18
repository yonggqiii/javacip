class c4204663 {

    public void testReadWrite() throws RuntimeException {
        final byte[] testBytes = "testString".getBytes();
        final InputStream istream = new ByteArrayInputStream(testBytes);
        final ByteArrayOutputStream destination = new ByteArrayOutputStream();
        final InputStream teeStream = new TeeInputStreamOutputStream(istream, destination);
        IOUtils.copy(teeStream, new NullOutputStream());
        teeStream.close();
        JavaCIPUnknownScope.assertArrayEquals("array are equals", testBytes, destination.toByteArray());
    }
}
