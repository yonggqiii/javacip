class c2102737 {

    private String getContents(Server server, String uri) throws TechnicalRuntimeException {
        try {
            URL url = new URL("http://localhost:" + JavaCIPUnknownScope.PORT + uri);
            return StreamUtils.getStreamContent(url.openStream());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new TechnicalRuntimeException(e);
        }
    }
}
