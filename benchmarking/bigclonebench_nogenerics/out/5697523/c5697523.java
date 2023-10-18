class c5697523 {

    private static void fileUpload() throws RuntimeException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        JavaCIPUnknownScope.file = new File("h:\\Fantastic face.jpg");
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        ContentBody cbFile = new FileBody(JavaCIPUnknownScope.file);
        mpEntity.addPart("MAX_FILE_SIZE", new StringBody("2147483647"));
        mpEntity.addPart("owner", new StringBody(""));
        mpEntity.addPart("pin", new StringBody(JavaCIPUnknownScope.pin));
        mpEntity.addPart("base", new StringBody(JavaCIPUnknownScope.base));
        mpEntity.addPart("host", new StringBody("letitbit.net"));
        mpEntity.addPart("file0", cbFile);
        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        System.out.println("Now uploading your file into letitbit.net");
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            JavaCIPUnknownScope.uploadresponse = EntityUtils.toString(resEntity);
        }
        System.out.println("Upload response : " + JavaCIPUnknownScope.uploadresponse);
    }
}
