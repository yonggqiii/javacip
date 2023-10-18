class c5998352 {

    public void testStandardTee() throws RuntimeException {
        final String reference = "test";
        final Reader source = new StringReader(reference);
        final StringWriter destination1 = new StringWriter();
        final StringWriter destination2 = new StringWriter();
        final TeeWriter tee = new TeeWriter(destination1, destination2);
        JavaCIPUnknownScope.org.apache.commons.io.IOUtils.copy(source, tee);
        tee.close();
        JavaCIPUnknownScope.assertEquals("the two string are equals", reference, destination1.toString());
        JavaCIPUnknownScope.assertEquals("the two string are equals", reference, destination2.toString());
        JavaCIPUnknownScope.assertEquals("byte count", reference.length(), tee.getSize());
    }
}