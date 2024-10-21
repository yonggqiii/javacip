


class c49209 {

    private void makeConn(String filename1, String filename2) {
        String basename = "http://www.bestmm.com/";
        String urlname = basename + filename1 + "/pic/" + filename2 + ".jpg";
        URL url = null;
        try {
            url = new URL(urlname);
        } catch (MalformedURLRuntimeException e) {
            System.err.println("URL Format Error!");
            System.exit(1);
        }
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IORuntimeException e) {
            System.err.println("Error IO");
            System.exit(2);
        }
    }

}
