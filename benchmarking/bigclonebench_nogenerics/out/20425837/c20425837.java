class c20425837 {

    public static String loadSite(String spec) throws IORuntimeException {
        URL url = new URL(spec);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String output = "";
        String str;
        while ((str = in.readLine()) != null) {
            output += str + "\n";
        }
        in.close();
        return output;
    }
}
