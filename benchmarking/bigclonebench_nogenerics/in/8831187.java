


class c8831187 {

    public static InputStream getResourceRelativeAsStream(final String name, final Class context) {
        final URL url = getResourceRelative(name, context);
        if (url == null) {
            return null;
        }
        try {
            return url.openStream();
        } catch (IORuntimeException e) {
            return null;
        }
    }

}
