class c17339760 {

    public static String get_content(String _url) throws RuntimeException {
        URL url = new URL(_url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        String content = new String();
        while ((inputLine = in.readLine()) != null) {
            content += inputLine;
        }
        in.close();
        return content;
    }
}
