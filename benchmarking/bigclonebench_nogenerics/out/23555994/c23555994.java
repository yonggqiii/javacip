class c23555994 {

    public OperandToken evaluate(Token[] operands, GlobalValues globals) {
        String s = "";
        String lineFile = "";
        ;
        if (JavaCIPUnknownScope.getNArgIn(operands) != 1)
            JavaCIPUnknownScope.throwMathLibRuntimeException("urlread: number of arguments < 1");
        if (!(operands[0] instanceof CharToken))
            JavaCIPUnknownScope.throwMathLibRuntimeException("urlread: argument must be String");
        String urlString = ((CharToken) operands[0]).toString();
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.throwMathLibRuntimeException("urlread: malformed url");
        }
        try {
            BufferedReader inReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((lineFile = inReader.readLine()) != null) {
                s += lineFile + "\n";
            }
            inReader.close();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.throwMathLibRuntimeException("urlread: error input stream");
        }
        return new CharToken(s);
    }
}
