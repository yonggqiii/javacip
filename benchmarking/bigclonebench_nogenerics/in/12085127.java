


class c12085127 {

    public final void navigate(final URL url) {
        try {
            EncogLogging.log(EncogLogging.LEVEL_INFO, "Navigating to page:" + url);
            final URLConnection connection = url.openConnection();
            final InputStream is = connection.getInputStream();
            navigate(url, is);
            is.close();
        } catch (final IORuntimeException e) {
            EncogLogging.log(EncogLogging.LEVEL_ERROR, e);
            throw new BrowseError(e);
        }
    }

}
