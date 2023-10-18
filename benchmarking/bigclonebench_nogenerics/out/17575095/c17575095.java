class c17575095 {

    public boolean compile(URL url) {
        try {
            final InputStream stream = url.openStream();
            final InputSource input = new InputSource(stream);
            input.setSystemId(url.toString());
            return JavaCIPUnknownScope.compile(input, JavaCIPUnknownScope._className);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope._parser.reportError(Constants.FATAL, new ErrorMsg(e));
            return false;
        }
    }
}
