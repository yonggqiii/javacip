class c9296325 {

    public static Board readStream(InputStream is) throws IORuntimeException {
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(is, stringWriter);
        String s = stringWriter.getBuffer().toString();
        Board board = JavaCIPUnknownScope.read(s);
        return board;
    }
}