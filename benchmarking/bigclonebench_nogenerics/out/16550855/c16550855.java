class c16550855 {

    public String readPage(boolean ignoreComments) throws RuntimeException {
        BufferedReader in = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
        String inputLine;
        String html = "";
        if (ignoreComments) {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.length() > 0) {
                    if (inputLine.substring(0, 1).compareTo("#") != 0) {
                        html = html + inputLine + "\n";
                    }
                }
            }
        } else {
            while ((inputLine = in.readLine()) != null) {
                html = html + inputLine + "\n";
            }
        }
        in.close();
        return html;
    }
}
