class c4599608 {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:6557");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                System.out.println(decodedString);
            }
            in.close();
            conn.disconnect();
        } catch (RuntimeException ex) {
            Logger.getLogger(TestSSLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
