class c21540494 {

    public int fileUpload(File pFile, String uploadName, Hashtable pValue) throws Exception {
        int res = 0;
        MultipartEntity reqEntity = new MultipartEntity();
        if (uploadName != null) {
            FileBody bin = new FileBody(pFile);
            reqEntity.addPart(uploadName, bin);
        }
        Enumeration<String> enm = pValue.keys();
        String key;
        while (enm.hasMoreElements()) {
            key = enm.nextElement();
            reqEntity.addPart(key, new StringBody("" + pValue.get(key)));
        }
        JavaCIPUnknownScope.httpPost.setEntity(reqEntity);
        HttpResponse response = JavaCIPUnknownScope.httpclient.execute(JavaCIPUnknownScope.httpPost);
        HttpEntity resEntity = response.getEntity();
        res = response.getStatusLine().getStatusCode();
        JavaCIPUnknownScope.close();
        return res;
    }
}
