class c6021010 {

    public static Dictionary parseVertices(URL url, Graph g) throws FileNotFoundRuntimeException, FlightRuntimeException {
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IORuntimeException e) {
            throw new FlightRuntimeException("IO Error: cannot read from URL " + url.toString());
        }
        Reader reader = new BufferedReader(new InputStreamReader(is));
        return Parser.parseVertices(reader, g);
    }
}
