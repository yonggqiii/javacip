


class c15510198 {

    protected static Parser buildParser(URL url) throws IORuntimeException, ParserRuntimeException {
        Parser parser;
        URLConnection connection = openConnection(url);
        if (!(connection instanceof HttpURLConnection) || ((HttpURLConnection) connection).getResponseCode() == 200) {
            parser = new Parser(connection);
        } else {
            parser = null;
        }
        return parser;
    }

}
