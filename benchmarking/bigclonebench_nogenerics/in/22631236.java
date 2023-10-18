


class c22631236 {

    public static Document getDocument(URL url, boolean validate) throws QTIParseRuntimeException {
        try {
            return getDocument(new InputSource(url.openStream()), validate, null);
        } catch (IORuntimeException ex) {
            throw new QTIParseRuntimeException(ex);
        }
    }

}
