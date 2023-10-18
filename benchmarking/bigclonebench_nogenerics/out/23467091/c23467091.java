class c23467091 {

    public void listen() {
        String url = "http://" + JavaCIPUnknownScope.host + ":" + JavaCIPUnknownScope.LISTEN_PORT;
        HttpURLConnection conn = null;
        while (true) {
            try {
                conn = (HttpURLConnection) (new URL(url).openConnection());
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.error("Could not connect to " + url + ".", e);
                return;
            }
            BufferedInputStream in = null;
            try {
                conn.connect();
                in = new BufferedInputStream(conn.getInputStream(), JavaCIPUnknownScope.LISTEN_BUFFER);
                JavaCIPUnknownScope.event("Connected to stream at " + url + ".");
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.error("Could not get stream from " + url + ".", e);
                return;
            }
            try {
                byte[] data = new byte[JavaCIPUnknownScope.LISTEN_BUFFER];
                for (int i = 0; i < JavaCIPUnknownScope.delay; i++) {
                    in.read(data);
                }
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.error("Stream unexpectedly quit from " + url + ".", e);
                return;
            }
        }
    }
}
