class c20916189 {

    public void fileUpload() throws RuntimeException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addPart("fff", new MonitoredFileBody(JavaCIPUnknownScope.file, JavaCIPUnknownScope.uploadProgress));
        httppost.setEntity(reqEntity);
        NULogger.getLogger().info("Now uploading your file into 2shared.com. Please wait......................");
        JavaCIPUnknownScope.status = UploadStatus.UPLOADING;
        HttpResponse response = httpclient.execute(httppost);
        JavaCIPUnknownScope.status = UploadStatus.GETTINGLINK;
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            String page = EntityUtils.toString(resEntity);
            NULogger.getLogger().log(Level.INFO, "PAGE :{0}", page);
        }
    }
}
