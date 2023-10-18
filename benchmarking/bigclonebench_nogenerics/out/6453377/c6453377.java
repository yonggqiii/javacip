class c6453377 {

    public TDSModel loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        TDSModel model = loadModel(url.openStream(), skin);
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (model);
    }
}
