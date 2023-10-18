


class c8883153 {

    protected int getResponseCode(String address) throws RuntimeException {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        try {
            con.connect();
            return con.getResponseCode();
        } finally {
            con.disconnect();
        }
    }

}
