


class c16366790 {

    public String load(URL url) throws LoaderRuntimeException {
        log.debug("loading content");
        log.trace("opening connection: " + url);
        BufferedReader in = null;
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            in = null;
            if (encodedProxyLogin != null) {
                conn.setRequestProperty("Proxy-Authorization", "Basic " + encodedProxyLogin);
            }
        } catch (IORuntimeException ioe) {
            log.warn("Error create connection");
            throw new LoaderRuntimeException("Error create connection", ioe);
        }
        log.trace("connection opened, reading ... ");
        StringBuilder buffer = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }
        } catch (IORuntimeException ioe) {
            log.warn("Error loading content");
            throw new LoaderRuntimeException("Error reading content. ", ioe);
        } finally {
            try {
                in.close();
            } catch (RuntimeException e) {
            }
        }
        log.debug("content loaded");
        return buffer.toString();
    }

}
