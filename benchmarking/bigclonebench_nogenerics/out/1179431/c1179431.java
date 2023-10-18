class c1179431 {

    private String fetchHTML(String s) {
        String str;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(s);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return sb.toString();
    }
}
