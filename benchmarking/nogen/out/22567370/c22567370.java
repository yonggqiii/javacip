class c22567370 {

    public static Reader createReader(TreeLogger logger, URL url) throws UnableToCompleteRuntimeException {
        try {
            return new InputStreamReader(url.openStream());
        } catch (IORuntimeException e) {
            logger.log(TreeLogger.ERROR, "Unable to open resource: " + url, e);
            throw new UnableToCompleteRuntimeException();
        }
    }
}
