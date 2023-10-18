class c23205 {

    public static BufferedReader getUserSolveStream(String name) throws IORuntimeException {
        BufferedReader in;
        try {
            URL url = new URL("http://www.spoj.pl/status/" + name.toLowerCase() + "/signedlist/");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (MalformedURLRuntimeException e) {
            in = null;
            throw e;
        }
        return in;
    }
}
