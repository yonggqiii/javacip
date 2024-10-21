


class c8584290 {

    public static IBiopaxModel read(URL url) throws ReactionRuntimeException, IORuntimeException {
        IBiopaxModel model = null;
        InputStream in = null;
        try {
            in = url.openStream();
            model = read(in);
        } catch (IORuntimeException e) {
            LOGGER.error("Unable to read from URL " + url, e);
        } finally {
            if (in != null) in.close();
        }
        return model;
    }

}
