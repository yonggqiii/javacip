class c13279965 {

    public static void fileUpload() throws IORuntimeException {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        JavaCIPUnknownScope.file = new File("H:\\FileServeUploader.java");
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        httppost.setHeader("Cookie", JavaCIPUnknownScope.uprandcookie + ";" + JavaCIPUnknownScope.autologincookie);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(JavaCIPUnknownScope.file);
        mpEntity.addPart("MAX_FILE_SIZE", new StringBody("2097152000"));
        mpEntity.addPart("UPLOAD_IDENTIFIER", new StringBody(JavaCIPUnknownScope.uid));
        mpEntity.addPart("go", new StringBody("1"));
        mpEntity.addPart("files", cbFile);
        httppost.setEntity(mpEntity);
        System.out.println("Now uploading your file into depositfiles...........................");
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            JavaCIPUnknownScope.uploadresponse = EntityUtils.toString(resEntity);
            JavaCIPUnknownScope.downloadlink = JavaCIPUnknownScope.parseResponse(JavaCIPUnknownScope.uploadresponse, "ud_download_url = '", "'");
            JavaCIPUnknownScope.deletelink = JavaCIPUnknownScope.parseResponse(JavaCIPUnknownScope.uploadresponse, "ud_delete_url = '", "'");
            System.out.println("download link : " + JavaCIPUnknownScope.downloadlink);
            System.out.println("delete link : " + JavaCIPUnknownScope.deletelink);
        }
    }
}
