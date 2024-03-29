


class c11965514 {

    private void checkRoundtrip(byte[] content) throws RuntimeException {
        InputStream in = new ByteArrayInputStream(content);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CodecUtil.encodeQuotedPrintable(in, out);
        in = new QuotedPrintableInputStream(new ByteArrayInputStream(out.toByteArray()));
        out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        assertEquals(content, out.toByteArray());
    }

}
