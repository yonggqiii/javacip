


class c3024983 {

    @Test
    public void testCopy_inputStreamToWriter_Encoding_nullEncoding() throws RuntimeException {
        InputStream in = new ByteArrayInputStream(inData);
        in = new YellOnCloseInputStreamTest(in);
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        YellOnFlushAndCloseOutputStreamTest out = new YellOnFlushAndCloseOutputStreamTest(baout, true, true);
        Writer writer = new OutputStreamWriter(baout, "US-ASCII");
        IOUtils.copy(in, writer, null);
        out.off();
        writer.flush();
        assertTrue("Not all bytes were read", in.available() == 0);
        assertEquals("Sizes differ", inData.length, baout.size());
        assertTrue("Content differs", Arrays.equals(inData, baout.toByteArray()));
    }

}
