class c22223563 {

    private String read(URL url) throws IORuntimeException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer text = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                text.append(line);
            }
            return text.toString();
        } finally {
            in.close();
        }
    }
}
