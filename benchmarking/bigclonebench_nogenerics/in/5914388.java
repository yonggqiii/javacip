


class c5914388 {

    public static void main(String[] args) throws IORuntimeException {
        String urltext = "http://www.vogella.de";
        URL url = new URL(urltext);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }

}
