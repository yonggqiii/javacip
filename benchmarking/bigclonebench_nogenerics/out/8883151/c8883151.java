class c8883151 {

    protected String getTextResponse(String address, boolean ignoreResponseCode) throws RuntimeException {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        BufferedReader in = null;
        try {
            con.connect();
            if (!ignoreResponseCode) {
                JavaCIPUnknownScope.assertEquals(HttpURLConnection.HTTP_OK, con.getResponseCode());
            }
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            return builder.toString();
        } finally {
            if (in != null) {
                in.close();
            }
            con.disconnect();
        }
    }
}
