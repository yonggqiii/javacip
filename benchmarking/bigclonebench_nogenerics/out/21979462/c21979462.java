class c21979462 {

    protected String downloadURLtoString(URL url) throws IORuntimeException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer sb = new StringBuffer(100 * 1024);
        String str;
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        return sb.toString();
    }
}
