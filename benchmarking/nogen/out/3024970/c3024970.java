class c3024970 {

    public void testCopy_inputStreamToOutputStream() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.inData);
        in = new YellOnCloseInputStreamTest(in);
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        OutputStream out = new YellOnFlushAndCloseOutputStreamTest(baout, false, true);
        int count = IOUtils.copy(in, out);
        JavaCIPUnknownScope.assertTrue("Not all bytes were read", in.available() == 0);
        JavaCIPUnknownScope.assertEquals("Sizes differ", JavaCIPUnknownScope.inData.length, baout.size());
        JavaCIPUnknownScope.assertTrue("Content differs", Arrays.equals(JavaCIPUnknownScope.inData, baout.toByteArray()));
    }
}
