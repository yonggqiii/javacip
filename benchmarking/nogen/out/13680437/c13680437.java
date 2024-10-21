class c13680437 {

    public static void writeFromURL(String urlstr, String filename) throws RuntimeException {
        URL url = new URL(urlstr);
        InputStream in = url.openStream();
        BufferedReader bf = null;
        StringBuffer sb = new StringBuffer();
        try {
            bf = new BufferedReader(new InputStreamReader(in, "latin1"));
            String s;
            while (true) {
                s = bf.readLine();
                if (s != null) {
                    sb.append(s);
                } else {
                    break;
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            bf.close();
        }
        JavaCIPUnknownScope.writeRawBytes(sb.toString(), filename);
    }
}
