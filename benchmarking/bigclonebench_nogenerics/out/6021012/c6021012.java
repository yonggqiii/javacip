class c6021012 {

    public static void parseEdges(URL url, Graph g, Dictionary airportToVertex) throws FileNotFoundRuntimeException, FlightRuntimeException {
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IORuntimeException e) {
            throw new FlightRuntimeException("IO Error: cannot read from URL " + url.toString());
        }
        Reader reader = new BufferedReader(new InputStreamReader(is));
        Parser.parseEdges(reader, g, airportToVertex);
    }
}
