class c13879984 {

    public static String readFromURL(String urlStr) throws IORuntimeException {
        URL url = new URL(urlStr);
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }
        in.close();
        return sb.toString();
    }
}
