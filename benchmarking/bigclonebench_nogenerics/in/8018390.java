


class c8018390 {

    protected String getCache() throws IORuntimeException {
        if (cache == null) {
            URL url = ((URI) hasContent()).toURL();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) ;
            in.close();
            cache = inputLine;
        }
        return cache;
    }

}
