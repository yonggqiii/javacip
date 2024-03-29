class c21540492 {

    public InputStream getStream(Hashtable<String, String> pValue) throws IOException {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Enumeration<String> enm = pValue.keys();
        String key;
        while (enm.hasMoreElements()) {
            key = enm.nextElement();
            nvps.add(new BasicNameValuePair(key, pValue.get(key)));
        }
        JavaCIPUnknownScope.httpPost.setEntity(new UrlEncodedFormEntity(nvps, JavaCIPUnknownScope.HTTP.UTF_8));
        HttpResponse response = JavaCIPUnknownScope.httpclient.execute(JavaCIPUnknownScope.httpPost);
        HttpEntity entity = response.getEntity();
        return entity.getContent();
    }
}
