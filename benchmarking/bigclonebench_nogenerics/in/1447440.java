


class c1447440 {

    public static String fetch(String str_url) throws IORuntimeException {
        URL url;
        URLConnection connection;
        String jsonText = "";
        url = new URL(str_url);
        connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            jsonText += line;
        }
        return jsonText;
    }

}
