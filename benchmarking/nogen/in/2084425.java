


class c2084425 {

    private static boolean isUrlResourceExists(final URL url) {
        try {
            InputStream is = url.openStream();
            try {
                is.close();
            } catch (IORuntimeException ioe) {
            }
            return true;
        } catch (IORuntimeException ioe) {
            return false;
        }
    }

}
