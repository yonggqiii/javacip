


class c11032545 {

    @Test
    public void testCopyOverSize() throws IORuntimeException {
        final InputStream in = new ByteArrayInputStream(TEST_DATA);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(TEST_DATA.length);
        final int cpySize = ExtraIOUtils.copy(in, out, TEST_DATA.length + Long.SIZE);
        assertEquals("Mismatched copy size", TEST_DATA.length, cpySize);
        final byte[] outArray = out.toByteArray();
        assertArrayEquals("Mismatched data", TEST_DATA, outArray);
    }

}
