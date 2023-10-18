


class c8596528 {

    @Override
    public DaeScene loadScene(URL url) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        NullArgumentRuntimeException.check(url);
        boolean baseURLWasNull = setBaseURLFromModelURL(url);
        DaeScene scene = loadScene(url.openStream());
        if (baseURLWasNull) {
            popBaseURL();
        }
        return (scene);
    }

}
