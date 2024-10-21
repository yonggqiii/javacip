class c5264337 {

    public AC3DModel loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        AC3DModel model = loadModel(url.openStream(), skin);
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (model);
    }
}
