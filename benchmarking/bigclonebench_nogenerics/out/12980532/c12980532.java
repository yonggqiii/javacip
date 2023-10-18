class c12980532 {

    public String htmlContentSimple(String urlStr, String charset) {
        StringBuffer html = new StringBuffer();
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL(urlStr);
            reader = new BufferedReader(new InputStreamReader(url.openStream(), charset));
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\r\n");
            }
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
        }
        return html.toString();
    }
}
