class c13821141 {

    public static String readUrlText(String urlString) throws IORuntimeException {
        URL url = new URL(urlString);
        InputStream stream = url.openStream();
        StringBuilder buf = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(stream));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
                buf.append(System.getProperty("line.separator"));
            }
        } catch (IORuntimeException e) {
            System.out.println("Error reading text from URL [" + url + "]: " + e.toString());
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    System.out.println("Error closing after reading text from URL [" + url + "]: " + e.toString());
                }
            }
        }
        return buf.toString();
    }
}
