class c11032545 {

    public void testCopyOverSize() throws IORuntimeException {
        final InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.TEST_DATA);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(JavaCIPUnknownScope.TEST_DATA.length);
        final int cpySize = ExtraIOUtils.copy(in, out, JavaCIPUnknownScope.TEST_DATA.length + Long.SIZE);
        JavaCIPUnknownScope.assertEquals("Mismatched copy size", JavaCIPUnknownScope.TEST_DATA.length, cpySize);
        final byte[] outArray = out.toByteArray();
        JavaCIPUnknownScope.assertArrayEquals("Mismatched data", JavaCIPUnknownScope.TEST_DATA, outArray);
    }
}
