class c11419428 {

    public static JSGFRuleGrammar newGrammarFromJSGF(URL url, JSGFRuleGrammarFactory factory) throws JSGFGrammarParseRuntimeException, IORuntimeException {
        Reader reader;
        BufferedInputStream stream = new BufferedInputStream(url.openStream(), 256);
        JSGFEncoding encoding = JavaCIPUnknownScope.getJSGFEncoding(stream);
        if ((encoding != null) && (encoding.encoding != null)) {
            System.out.println("Grammar Character Encoding \"" + encoding.encoding + "\"");
            reader = new InputStreamReader(stream, encoding.encoding);
        } else {
            if (encoding == null)
                System.out.println("WARNING: Grammar missing self identifying header");
            reader = new InputStreamReader(stream);
        }
        return newGrammarFromJSGF(reader, factory);
    }
}