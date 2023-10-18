class c3662475 {

    public static String getHtml(DefaultHttpClient httpclient, String url, String encode) throws IORuntimeException {
        InputStream input = null;
        HttpGet get = new HttpGet(url);
        HttpResponse res = httpclient.execute(get);
        StatusLine status = res.getStatusLine();
        if (status.getStatusCode() != JavaCIPUnknownScope.STATUSCODE_200) {
            throw new RuntimeRuntimeException("50001");
        }
        if (res.getEntity() == null) {
            return "";
        }
        input = res.getEntity().getContent();
        InputStreamReader reader = new InputStreamReader(input, encode);
        BufferedReader bufReader = new BufferedReader(reader);
        String tmp = null, html = "";
        while ((tmp = bufReader.readLine()) != null) {
            html += tmp;
        }
        if (input != null) {
            input.close();
        }
        return html;
    }
}
