class c6453383 {

    public TDSScene loadScene(URL url) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        TDSScene scene = loadScene(url.openStream());
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (scene);
    }
}
