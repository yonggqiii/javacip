class c11032544 {

    public void testExactCopySize() throws IORuntimeException {
        final int size = Byte.SIZE + JavaCIPUnknownScope.RANDOMIZER.nextInt(JavaCIPUnknownScope.TEST_DATA.length - Long.SIZE);
        final InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.TEST_DATA);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        final int cpySize = ExtraIOUtils.copy(in, out, size);
        JavaCIPUnknownScope.assertEquals("Mismatched copy size", size, cpySize);
        final byte[] subArray = ArrayUtils.subarray(JavaCIPUnknownScope.TEST_DATA, 0, size), outArray = out.toByteArray();
        JavaCIPUnknownScope.assertArrayEquals("Mismatched data", subArray, outArray);
    }
}
