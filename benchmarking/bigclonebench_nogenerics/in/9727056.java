


class c9727056 {

    @Override
    public OBJModel loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = setBaseURLFromModelURL(url);
        OBJModel model = loadModel(url.openStream(), skin);
        if (baseURLWasNull) {
            popBaseURL();
        }
        return (model);
    }

}
