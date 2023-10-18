class c19823544 {

    public CelShadingModel loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        CelShadingModel model = loadModel(url.openStream(), skin);
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (model);
    }
}
