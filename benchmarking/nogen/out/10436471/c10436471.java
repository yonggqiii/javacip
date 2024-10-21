class c10436471 {

    public Cal3dModel loadModel(URL url, String skin) throws IORuntimeException, IncorrectFormatRuntimeException, ParsingErrorRuntimeException {
        boolean baseURLWasNull = JavaCIPUnknownScope.setBaseURLFromModelURL(url);
        Cal3dModel model = new Cal3dModel(JavaCIPUnknownScope.getFlags());
        JavaCIPUnknownScope.loadCal3dModel(JavaCIPUnknownScope.getBaseURL(), url.toExternalForm(), new InputStreamReader(url.openStream()), model);
        if (baseURLWasNull) {
            JavaCIPUnknownScope.popBaseURL();
        }
        return (model);
    }
}
