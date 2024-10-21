


class c6595223 {

    private void testConnection(String address) throws RuntimeException {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        try {
            con.connect();
            assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
        } finally {
            con.disconnect();
        }
    }

}
