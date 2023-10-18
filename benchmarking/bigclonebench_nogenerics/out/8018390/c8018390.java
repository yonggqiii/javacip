class c8018390 {

    protected String getCache() throws IORuntimeException {
        if (JavaCIPUnknownScope.cache == null) {
            URL url = ((URI) JavaCIPUnknownScope.hasContent()).toURL();
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) ;
            in.close();
            JavaCIPUnknownScope.cache = inputLine;
        }
        return JavaCIPUnknownScope.cache;
    }
}
