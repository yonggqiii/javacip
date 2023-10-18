class c17341833 {

    private static InputSource getInputSourceFromURI(String uri, String username, String password) throws IORuntimeException, ProtocolRuntimeException, UnsupportedEncodingRuntimeException {
        URL wsdlurl = null;
        try {
            wsdlurl = new URL(uri);
        } catch (MalformedURLRuntimeException e) {
            return new InputSource(uri);
        }
        if (username == null && wsdlurl.getUserInfo() == null) {
            return new InputSource(uri);
        }
        if (!wsdlurl.getProtocol().startsWith("http")) {
            return new InputSource(uri);
        }
        URLConnection connection = wsdlurl.openConnection();
        if (!(connection instanceof HttpURLConnection)) {
            return new InputSource(uri);
        }
        HttpURLConnection uconn = (HttpURLConnection) connection;
        String userinfo = wsdlurl.getUserInfo();
        uconn.setRequestMethod("GET");
        uconn.setAllowUserInteraction(false);
        uconn.setDefaultUseCaches(false);
        uconn.setDoInput(true);
        uconn.setDoOutput(false);
        uconn.setInstanceFollowRedirects(true);
        uconn.setUseCaches(false);
        String auth = null;
        if (userinfo != null) {
            auth = userinfo;
        } else if (username != null) {
            auth = (password == null) ? username : username + ":" + password;
        }
        if (auth != null) {
            uconn.setRequestProperty("Authorization", "Basic " + JavaCIPUnknownScope.base64encode(auth.getBytes(JavaCIPUnknownScope.charEncoding)));
        }
        uconn.connect();
        return new InputSource(uconn.getInputStream());
    }
}
