class c23482443 {

    public static String getDocumentAsString(URL url) throws IORuntimeException {
        StringBuffer result = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
        String line = "";
        while (line != null) {
            result.append(line);
            line = in.readLine();
        }
        return result.toString();
    }
}
