class c19667000 {

    public static Reader getReader(String url) throws MalformedURLRuntimeException, IORuntimeException {
        if (url.startsWith("file:"))
            return new FileReader(url.substring(5));
        else if (url.startsWith("http:"))
            return new InputStreamReader(new URL(url).openStream());
        throw new MalformedURLRuntimeException("Invalid URI schema, file: or http: expected.");
    }
}
