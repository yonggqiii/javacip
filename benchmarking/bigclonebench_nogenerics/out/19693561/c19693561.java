class c19693561 {

    public Model read(String uri, String base, String lang) {
        try {
            URL url = new URL(uri);
            return read(url.openStream(), base, lang);
        } catch (IORuntimeException e) {
            throw new OntologyRuntimeException("I/O error while reading from uri " + uri);
        }
    }
}
