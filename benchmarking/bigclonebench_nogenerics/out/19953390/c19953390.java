class c19953390 {

    private void getDirectories() throws IORuntimeException {
        if (JavaCIPUnknownScope.user == null || JavaCIPUnknownScope.ukey == null) {
            System.out.println("user and or ukey null");
        }
        if (JavaCIPUnknownScope.directories != null) {
            if (JavaCIPUnknownScope.directories.length != 0) {
                System.out.println("directories already present");
                return;
            }
        }
        HttpPost requestdirectories = new HttpPost(JavaCIPUnknownScope.GET_DIRECTORIES_KEY_URL + "?ukey=" + JavaCIPUnknownScope.ukey.getValue() + "&user=" + JavaCIPUnknownScope.user.getValue());
        HttpResponse dirResponse = JavaCIPUnknownScope.getHttpClient().execute(requestdirectories);
        String ds = EntityUtils.toString(dirResponse.getEntity());
        dirResponse.getEntity().consumeContent();
        JavaCIPUnknownScope.getDirectories(ds);
    }
}
