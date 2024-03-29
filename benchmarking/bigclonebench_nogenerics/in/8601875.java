


class c8601875 {

    public void init() throws RuntimeException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int code = conn.getResponseCode();
        if (code != 200) throw new IORuntimeException("Error fetching robots.txt; respose code is " + code);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String buff;
        StringBuilder builder = new StringBuilder();
        while ((buff = reader.readLine()) != null) builder.append(buff);
        parseRobots(builder.toString());
    }

}
