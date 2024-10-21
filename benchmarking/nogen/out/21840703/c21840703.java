class c21840703 {

    public void parse() throws RuntimeException {
        URL url = new URL("http://www.oki.com");
        HtmlParser parser = new HtmlParser();
        byte[] bytes = FileUtilities.getContents(url.openStream(), Integer.MAX_VALUE).toByteArray();
        OutputStream parsed = parser.parse(new ByteArrayInputStream(bytes), new ByteArrayOutputStream());
        JavaCIPUnknownScope.assertTrue(parsed.toString().indexOf("Oki") > -1);
    }
}
