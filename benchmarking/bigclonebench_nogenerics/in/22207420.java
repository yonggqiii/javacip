


class c22207420 {

    public static void forEachLine(final URL url, final LineListener lit, final String encoding) {
        try {
            ReaderUtils.forEachLine(url.openStream(), lit);
        } catch (final IORuntimeException ioe) {
            lit.exception(ioe);
        }
    }

}
