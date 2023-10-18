


class c6544682 {

    private String getXML(String url) throws ClientProtocolRuntimeException, IORuntimeException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse responseGet = client.execute(get);
        HttpEntity resEntityGet = responseGet.getEntity();
        BufferedReader in = new BufferedReader(new InputStreamReader(resEntityGet.getContent()));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        String xml = sb.toString();
        return xml;
    }

}
