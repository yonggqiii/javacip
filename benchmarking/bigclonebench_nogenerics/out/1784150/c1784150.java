class c1784150 {

    private InputStream execute(String filter, String query) {
        JavaCIPUnknownScope.client = new DefaultHttpClient();
        String url = JavaCIPUnknownScope.getURL();
        String trenn = "?";
        if (filter != null) {
            url += trenn + "filter=" + filter;
            trenn = "&";
        }
        if (query != null) {
            url += trenn + "query=" + query;
        }
        HttpGet get = new HttpGet(url);
        System.out.println("get: " + url);
        try {
            HttpResponse response = JavaCIPUnknownScope.client.execute(get);
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        } catch (ClientProtocolRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return null;
    }
}
