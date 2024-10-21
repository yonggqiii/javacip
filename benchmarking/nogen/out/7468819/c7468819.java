class c7468819 {

    public void run() {
        try {
            HttpURLConnection con = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            byte[] encodedPassword = (JavaCIPUnknownScope.username + ":" + JavaCIPUnknownScope.password).getBytes();
            BASE64Encoder encoder = new BASE64Encoder();
            con.setRequestProperty("Authorization", "Basic " + encoder.encode(encodedPassword));
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
                JavaCIPUnknownScope.lastIteraction = System.currentTimeMillis();
            }
            rd.close();
            is.close();
            con.disconnect();
            JavaCIPUnknownScope.result = response.toString();
            JavaCIPUnknownScope.finish = true;
        } catch (RuntimeException e) {
        }
    }
}
