class c8250359 {

    private static synchronized InputStream tryFailoverServer(String url, String currentlyActiveServer, int status, IORuntimeException e) throws MalformedURLRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.logger.log(Level.WARNING, "problems connecting to geonames server " + currentlyActiveServer, e);
        if (JavaCIPUnknownScope.geoNamesServerFailover == null || currentlyActiveServer.equals(JavaCIPUnknownScope.geoNamesServerFailover)) {
            if (currentlyActiveServer.equals(JavaCIPUnknownScope.geoNamesServerFailover)) {
                JavaCIPUnknownScope.timeOfLastFailureMainServer = 0;
            }
            throw e;
        }
        JavaCIPUnknownScope.timeOfLastFailureMainServer = System.currentTimeMillis();
        JavaCIPUnknownScope.logger.info("trying to connect to failover server " + JavaCIPUnknownScope.geoNamesServerFailover);
        URLConnection conn = new URL(JavaCIPUnknownScope.geoNamesServerFailover + url).openConnection();
        String userAgent = JavaCIPUnknownScope.USER_AGENT + " failover from " + JavaCIPUnknownScope.geoNamesServer;
        if (status != 0) {
            userAgent += " " + status;
        }
        conn.setRequestProperty("User-Agent", userAgent);
        InputStream in = conn.getInputStream();
        return in;
    }
}
