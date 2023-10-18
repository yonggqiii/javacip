class c8596528 {

    public DaeScene loadScene(URL url) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        NullArgumentRuntimeException.check(url);
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        DaeScene scene = loadScene(url.openStream());
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (scene);
    }
}
