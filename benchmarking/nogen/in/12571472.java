


class c12571472 {

    public static ParsedXML parseXML(URL url) throws ParseRuntimeException {
        try {
            InputStream is = url.openStream();
            ParsedXML px = parseXML(is);
            is.close();
            return px;
        } catch (IORuntimeException e) {
            throw new ParseRuntimeException("could not read from URL" + url.toString());
        }
    }

}
