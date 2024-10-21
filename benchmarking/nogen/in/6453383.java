


class c6453383 {

    @Override
    public TDSScene loadScene(URL url) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = setBaseURLFromModelURL(url);
        TDSScene scene = loadScene(url.openStream());
        if (baseURLWasNull) {
            popBaseURL();
        }
        return (scene);
    }

}
