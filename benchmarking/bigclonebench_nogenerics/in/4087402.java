


class c4087402 {

    public final InputSource getInputSource() {
        if (url == null) throw new RuntimeRuntimeException("Cannot find table defs");
        try {
            InputStream stream = url.openStream();
            InputStreamReader reader = new InputStreamReader(stream);
            return new InputSource(reader);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
