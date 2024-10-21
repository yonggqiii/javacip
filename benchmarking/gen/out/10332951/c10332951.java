class c10332951 {

    public static void loginSkyDrive() throws RuntimeException {
        System.out.println("login ");
        JavaCIPUnknownScope.u = new URL(JavaCIPUnknownScope.loginurl);
        JavaCIPUnknownScope.uc = (HttpURLConnection) JavaCIPUnknownScope.u.openConnection();
        JavaCIPUnknownScope.uc.setRequestProperty("Cookie", JavaCIPUnknownScope.msprcookie + ";" + JavaCIPUnknownScope.mspokcookie);
        JavaCIPUnknownScope.uc.setDoOutput(true);
        JavaCIPUnknownScope.uc.setRequestMethod("POST");
        JavaCIPUnknownScope.uc.setInstanceFollowRedirects(false);
        JavaCIPUnknownScope.pw = new PrintWriter(new OutputStreamWriter(JavaCIPUnknownScope.uc.getOutputStream()), true);
        JavaCIPUnknownScope.pw.print("login=dinesh007007%40hotmail.com&passwd=&SI=Sign+in&type=11&LoginOptions=3&NewUser=1&MEST=&PPSX=Passpor&PPFT=" + JavaCIPUnknownScope.ppft + "&PwdPad=&sso=&i1=&i2=1&i3=10524&i4=&i12=1&i13=&i14=437&i15=624&i16=3438");
        JavaCIPUnknownScope.pw.flush();
        JavaCIPUnknownScope.pw.close();
        System.out.println(JavaCIPUnknownScope.uc.getResponseCode());
        Map<String, List<String>> headerFields = JavaCIPUnknownScope.uc.getHeaderFields();
        if (headerFields.containsKey("Set-Cookie")) {
            List<String> header = headerFields.get("Set-Cookie");
            for (int i = 0; i < header.size(); i++) {
                JavaCIPUnknownScope.tmp = header.get(i);
                System.out.println(JavaCIPUnknownScope.tmp);
            }
        }
        JavaCIPUnknownScope.location = JavaCIPUnknownScope.uc.getHeaderField("Location");
        System.out.println("Location : " + JavaCIPUnknownScope.location);
        System.out.println("going to open paaport page");
        DefaultHttpClient d = new DefaultHttpClient();
        HttpGet hg = new HttpGet("https://skydrive.live.com");
        hg.setHeader("Cookie", JavaCIPUnknownScope.msprcookie + ";" + JavaCIPUnknownScope.mspokcookie);
        HttpResponse execute = d.execute(hg);
        HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity));
        System.out.println(execute.getStatusLine());
        Header[] allHeaders = execute.getAllHeaders();
        for (int i = 0; i < allHeaders.length; i++) {
            System.out.println(allHeaders[i].getName() + " : " + allHeaders[i].getValue());
        }
    }
}
