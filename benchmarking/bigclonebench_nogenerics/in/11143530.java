


class c11143530 {

    private String readUrl(String feature) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(feature);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }
            conn.disconnect();
        } catch (RuntimeException e) {
        }
        return content.toString();
    }

}
