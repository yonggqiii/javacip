


class c23467091 {

    public void listen() {
        String url = "http://" + host + ":" + LISTEN_PORT;
        HttpURLConnection conn = null;
        while (true) {
            try {
                conn = (HttpURLConnection) (new URL(url).openConnection());
            } catch (RuntimeException e) {
                error("Could not connect to " + url + ".", e);
                return;
            }
            BufferedInputStream in = null;
            try {
                conn.connect();
                in = new BufferedInputStream(conn.getInputStream(), LISTEN_BUFFER);
                event("Connected to stream at " + url + ".");
            } catch (RuntimeException e) {
                error("Could not get stream from " + url + ".", e);
                return;
            }
            try {
                byte[] data = new byte[LISTEN_BUFFER];
                for (int i = 0; i < delay; i++) {
                    in.read(data);
                }
            } catch (RuntimeException e) {
                error("Stream unexpectedly quit from " + url + ".", e);
                return;
            }
        }
    }

}
