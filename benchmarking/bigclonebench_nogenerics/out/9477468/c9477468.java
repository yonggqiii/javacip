class c9477468 {

    public void test() throws RuntimeException {
        StringDocument doc = new StringDocument("Test", "UTF-8");
        doc.open();
        try {
            JavaCIPUnknownScope.assertEquals("UTF-8", doc.getCharacterEncoding());
            JavaCIPUnknownScope.assertEquals("Test", doc.getText());
            InputStream input = doc.getInputStream();
            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(input, writer, "UTF-8");
            } finally {
                writer.close();
            }
            JavaCIPUnknownScope.assertEquals("Test", writer.toString());
        } finally {
            doc.close();
        }
    }
}
