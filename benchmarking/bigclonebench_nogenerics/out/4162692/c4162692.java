class c4162692 {

    private static void fileUpload() throws IORuntimeException {
        HttpClient httpclient = new DefaultHttpClient();
        if (JavaCIPUnknownScope.login) {
            JavaCIPUnknownScope.postURL = "http://upload.badongo.com/mpu_upload.php";
        }
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        JavaCIPUnknownScope.file = new File("g:/S2SClient.7z");
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentBody cbFile = new FileBody(JavaCIPUnknownScope.file);
        mpEntity.addPart("Filename", new StringBody(JavaCIPUnknownScope.file.getName()));
        if (JavaCIPUnknownScope.login) {
            mpEntity.addPart("PHPSESSID", new StringBody(JavaCIPUnknownScope.dataid));
        }
        mpEntity.addPart("Filedata", cbFile);
        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Now uploading your file into badongo.com");
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        System.out.println("Upload response : " + JavaCIPUnknownScope.uploadresponse);
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            JavaCIPUnknownScope.uploadresponse = EntityUtils.toString(resEntity);
        }
        System.out.println("res " + JavaCIPUnknownScope.uploadresponse);
        httpclient.getConnectionManager().shutdown();
    }
}
