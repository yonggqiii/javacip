class c14610316 {

    public static void fileUpload() throws IORuntimeException {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        JavaCIPUnknownScope.file = new File("H:\\FileServeUploader.java");
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        httppost.setHeader("Cookie", JavaCIPUnknownScope.langcookie + ";" + JavaCIPUnknownScope.sessioncookie + ";" + JavaCIPUnknownScope.mailcookie + ";" + JavaCIPUnknownScope.namecookie + ";" + JavaCIPUnknownScope.rolecookie + ";" + JavaCIPUnknownScope.orderbycookie + ";" + JavaCIPUnknownScope.directioncookie + ";");
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(JavaCIPUnknownScope.file);
        mpEntity.addPart("files[]", cbFile);
        httppost.setEntity(mpEntity);
        System.out.println("Now uploading your file into wupload...........................");
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        System.out.println(response.getStatusLine());
    }
}
