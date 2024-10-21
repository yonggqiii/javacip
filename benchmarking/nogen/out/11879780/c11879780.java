class c11879780 {

    public MD2Model loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        MD2Model model = loadModel(url.openStream(), skin);
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (model);
    }
}
