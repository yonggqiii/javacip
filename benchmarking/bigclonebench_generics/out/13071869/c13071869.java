class c13071869 {

    private void loginImageShack() throws Exception {
        JavaCIPUnknownScope.loginsuccessful = false;
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.useragent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        NULogger.getLogger().info("Trying to log in to imageshack.us");
        HttpPost httppost = new HttpPost("http://imageshack.us/auth.php");
        httppost.setHeader("Referer", "http://www.uploading.com/");
        httppost.setHeader("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
        httppost.setHeader("Cookie", JavaCIPUnknownScope.newcookie + ";" + JavaCIPUnknownScope.phpsessioncookie + ";" + JavaCIPUnknownScope.imgshckcookie + ";" + JavaCIPUnknownScope.uncookie + ";" + JavaCIPUnknownScope.latestcookie + ";" + JavaCIPUnknownScope.langcookie);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", JavaCIPUnknownScope.getUsername()));
        formparams.add(new BasicNameValuePair("password", JavaCIPUnknownScope.getPassword()));
        formparams.add(new BasicNameValuePair("stay_logged_in", ""));
        formparams.add(new BasicNameValuePair("format", "json"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        httppost.setEntity(entity);
        HttpResponse httpresponse = httpclient.execute(httppost);
        HttpEntity en = httpresponse.getEntity();
        JavaCIPUnknownScope.uploadresponse = EntityUtils.toString(en);
        NULogger.getLogger().log(Level.INFO, "Upload response : {0}", JavaCIPUnknownScope.uploadresponse);
        NULogger.getLogger().info("Getting cookies........");
        Iterator<Cookie> it = httpclient.getCookieStore().getCookies().iterator();
        Cookie escookie = null;
        while (it.hasNext()) {
            escookie = it.next();
            if (escookie.getName().equalsIgnoreCase("myid")) {
                JavaCIPUnknownScope.myidcookie = escookie.getValue();
                NULogger.getLogger().info(JavaCIPUnknownScope.myidcookie);
                JavaCIPUnknownScope.loginsuccessful = true;
            }
            if (escookie.getName().equalsIgnoreCase("myimages")) {
                JavaCIPUnknownScope.myimagescookie = escookie.getValue();
                NULogger.getLogger().info(JavaCIPUnknownScope.myimagescookie);
            }
            if (escookie.getName().equalsIgnoreCase("isUSER")) {
                JavaCIPUnknownScope.usercookie = escookie.getValue();
                NULogger.getLogger().info(JavaCIPUnknownScope.usercookie);
            }
        }
        if (JavaCIPUnknownScope.loginsuccessful) {
            NULogger.getLogger().info("ImageShack Login Success");
            JavaCIPUnknownScope.loginsuccessful = true;
            JavaCIPUnknownScope.username = JavaCIPUnknownScope.getUsername();
            JavaCIPUnknownScope.password = JavaCIPUnknownScope.getPassword();
        } else {
            NULogger.getLogger().info("ImageShack Login failed");
            JavaCIPUnknownScope.loginsuccessful = false;
            JavaCIPUnknownScope.username = "";
            JavaCIPUnknownScope.password = "";
            JOptionPane.showMessageDialog(NeembuuUploader.getInstance(), "<html><b>" + JavaCIPUnknownScope.HOSTNAME + "</b> " + TranslationProvider.get("neembuuuploader.accounts.loginerror") + "</html>", JavaCIPUnknownScope.HOSTNAME, JOptionPane.WARNING_MESSAGE);
            AccountsManager.getInstance().setVisible(true);
        }
    }
}
