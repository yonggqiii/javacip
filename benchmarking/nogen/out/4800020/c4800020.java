class c4800020 {

    private void download(String groupId, String artifactId, String version, String type) throws ClientProtocolRuntimeException, IORuntimeException {
        String finalName = artifactId + "-" + version;
        File file = new File(JavaCIPUnknownScope.deployables, groupId + "/" + artifactId + "/" + version + "/" + finalName + "." + type);
        if (file.exists()) {
            JavaCIPUnknownScope.log.warn("Won't download {} found at {}", finalName, file.getAbsolutePath());
            return;
        }
        String url = JavaCIPUnknownScope.repository + groupId + "/" + artifactId + "/" + version + "/" + finalName + "." + type;
        HttpGet get = new HttpGet(url);
        HttpResponse response = JavaCIPUnknownScope.httpclient.execute(get);
        if (response.getStatusLine().getStatusCode() == 200)
            JavaCIPUnknownScope.writeContent(get, response.getEntity(), file);
        else
            throw new RuntimeRuntimeException("Failed to download " + url + " due to error " + response.getStatusLine());
    }
}
