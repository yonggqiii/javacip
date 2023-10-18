class c22682349 {

    public static Dictionary getDefaultConfig(BundleContext bc) {
        final Dictionary config = new Hashtable();
        config.put(HttpConfig.HTTP_ENABLED_KEY, JavaCIPUnknownScope.getPropertyAsBoolean(bc, "org.knopflerfish.http.enabled", "true"));
        config.put(HttpConfig.HTTPS_ENABLED_KEY, JavaCIPUnknownScope.getPropertyAsBoolean(bc, "org.knopflerfish.http.secure.enabled", "true"));
        config.put(HttpConfig.HTTP_PORT_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.osgi.service.http.port", JavaCIPUnknownScope.HTTP_PORT_DEFAULT));
        config.put(HttpConfig.HTTPS_PORT_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.osgi.service.http.secure.port", JavaCIPUnknownScope.HTTPS_PORT_DEFAULT));
        config.put(HttpConfig.HOST_KEY, JavaCIPUnknownScope.getPropertyAsString(bc, "org.osgi.service.http.hostname", ""));
        Properties mimeProps = new Properties();
        try {
            mimeProps.load(HttpConfig.class.getResourceAsStream("/mime.default"));
            String propurl = JavaCIPUnknownScope.getPropertyAsString(bc, "org.knopflerfish.http.mime.props", "");
            if (propurl.length() > 0) {
                URL url = new URL(propurl);
                Properties userMimeProps = new Properties();
                userMimeProps.load(url.openStream());
                Enumeration e = userMimeProps.keys();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    mimeProps.put(key, userMimeProps.getProperty(key));
                }
            }
        } catch (MalformedURLRuntimeException ignore) {
        } catch (IORuntimeException ignore) {
        }
        Vector mimeVector = new Vector(mimeProps.size());
        Enumeration e = mimeProps.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            mimeVector.addElement(new String[] { key, mimeProps.getProperty(key) });
        }
        config.put(HttpConfig.MIME_PROPS_KEY, mimeVector);
        config.put(HttpConfig.SESSION_TIMEOUT_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.knopflerfish.http.session.timeout.default", 1200));
        config.put(HttpConfig.CONNECTION_TIMEOUT_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.knopflerfish.http.connection.timeout", 30));
        config.put(HttpConfig.CONNECTION_MAX_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.knopflerfish.http.connection.max", 50));
        config.put(HttpConfig.DNS_LOOKUP_KEY, JavaCIPUnknownScope.getPropertyAsBoolean(bc, "org.knopflerfish.http.dnslookup", "false"));
        config.put(HttpConfig.RESPONSE_BUFFER_SIZE_DEFAULT_KEY, JavaCIPUnknownScope.getPropertyAsInteger(bc, "org.knopflerfish.http.response.buffer.size.default", 16384));
        config.put(HttpConfig.DEFAULT_CHAR_ENCODING_KEY, JavaCIPUnknownScope.getPropertyAsString(bc, HttpConfig.DEFAULT_CHAR_ENCODING_KEY, "ISO-8859-1"));
        config.put(HttpConfig.REQ_CLIENT_AUTH_KEY, JavaCIPUnknownScope.getPropertyAsBoolean(bc, "org.knopflerfish.http.req.client.auth", "false"));
        return config;
    }
}
