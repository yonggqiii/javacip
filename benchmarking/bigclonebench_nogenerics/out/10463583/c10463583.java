class c10463583 {

    public static Document getXHTMLDocument(URL _url) throws IORuntimeException {
        final Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setXmlOut(true);
        final BufferedInputStream input_stream = new BufferedInputStream(_url.openStream());
        return tidy.parseDOM(input_stream, null);
    }
}
