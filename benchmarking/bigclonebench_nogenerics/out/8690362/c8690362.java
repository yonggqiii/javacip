class c8690362 {

    public static void fileUpload() throws IORuntimeException {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(JavaCIPUnknownScope.postURL);
        File file = new File("d:/hai.html");
        System.out.println(JavaCIPUnknownScope.ukeycookie);
        httppost.setHeader("Cookie", JavaCIPUnknownScope.ukeycookie + ";" + JavaCIPUnknownScope.skeycookie + ";" + JavaCIPUnknownScope.usercookie);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(file);
        mpEntity.addPart("", cbFile);
        httppost.setEntity(mpEntity);
        System.out.println("Now uploading your file into mediafire...........................");
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println("Getting upload response key value..........");
            JavaCIPUnknownScope.uploadresponsekey = EntityUtils.toString(resEntity);
            JavaCIPUnknownScope.getUploadResponseKey();
            System.out.println("upload resoponse key " + JavaCIPUnknownScope.uploadresponsekey);
        }
    }
}
