


class c23206 {

    public static BufferedReader getUserInfoStream(String name) throws IORuntimeException {
        BufferedReader in;
        try {
            URL url = new URL("http://www.spoj.pl/users/" + name.toLowerCase() + "/");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (MalformedURLRuntimeException e) {
            in = null;
            throw e;
        }
        return in;
    }

}
