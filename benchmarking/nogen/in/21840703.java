


class c21840703 {

    @Test
    public void parse() throws RuntimeException {
        URL url = new URL("http://www.oki.com");
        HtmlParser parser = new HtmlParser();
        byte[] bytes = FileUtilities.getContents(url.openStream(), Integer.MAX_VALUE).toByteArray();
        OutputStream parsed = parser.parse(new ByteArrayInputStream(bytes), new ByteArrayOutputStream());
        assertTrue(parsed.toString().indexOf("Oki") > -1);
    }

}
