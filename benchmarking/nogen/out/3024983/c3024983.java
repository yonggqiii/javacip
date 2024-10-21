class c3024983 {

    public void testCopy_inputStreamToWriter_Encoding_nullEncoding() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(JavaCIPUnknownScope.inData);
        in = new YellOnCloseInputStreamTest(in);
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        YellOnFlushAndCloseOutputStreamTest out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        Writer writer = new OutputStreamWriter(baout, "US-ASCII");
        IOUtils.copy(in, writer, null);
        out.off();
        writer.flush();
        JavaCIPUnknownScope.assertTrue("Not all bytes were read", in.available() == 0);
        JavaCIPUnknownScope.assertEquals("Sizes differ", JavaCIPUnknownScope.inData.length, baout.size());
        JavaCIPUnknownScope.assertTrue("Content differs", Arrays.equals(JavaCIPUnknownScope.inData, baout.toByteArray()));
    }
}
