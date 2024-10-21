class c2459317 {

    public void testStandardTee() throws RuntimeException {
        final byte[] test = "test".getBytes();
        final InputStream source = new ByteArrayInputStream(test);
        final ByteArrayOutputStream destination1 = new ByteArrayOutputStream();
        final ByteArrayOutputStream destination2 = new ByteArrayOutputStream();
        final TeeOutputStream tee = new TeeOutputStream(destination1, destination2);
        JavaCIPUnknownScope.org.apache.commons.io.IOUtils.copy(source, tee);
        tee.close();
        JavaCIPUnknownScope.assertArrayEquals("the two arrays are equals", test, destination1.toByteArray());
        JavaCIPUnknownScope.assertArrayEquals("the two arrays are equals", test, destination2.toByteArray());
        JavaCIPUnknownScope.assertEquals("byte count", test.length, tee.getSize());
    }
}
