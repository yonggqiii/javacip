class c3024990 {

    public void testCopy_readerToOutputStream_Encoding_nullEncoding() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.inData);
        in = new YellOnCloseInputStreamTest(in);
        Reader reader = new InputStreamReader(in, "US-ASCII");
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, false, true);
        IOUtils.copy(reader, out, null);
        JavaCIPUnknownScope.assertEquals("Sizes differ", JavaCIPUnknownScope.inData.length, baout.size());
        JavaCIPUnknownScope.assertTrue("Content differs", Arrays.equals(JavaCIPUnknownScope.inData, baout.toByteArray()));
    }
}
