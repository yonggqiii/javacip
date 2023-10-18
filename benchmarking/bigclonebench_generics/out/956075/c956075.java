class c956075 {

    public void loginSendSpace() throws Exception {
        JavaCIPUnknownScope.loginsuccessful = false;
        HttpParams params = new BasicHttpParams();
        params.setParameter("http.useragent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        NULogger.getLogger().info("Trying to log in to sendspace");
        HttpPost httppost = new HttpPost("http://www.sendspace.com/login.html");
        httppost.setHeader("Cookie", JavaCIPUnknownScope.sidcookie + ";" + JavaCIPUnknownScope.ssuicookie);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("action", "login"));
        formparams.add(new BasicNameValuePair("submit", "login"));
        formparams.add(new BasicNameValuePair("target", "%252F"));
        formparams.add(new BasicNameValuePair("action_type", "login"));
        formparams.add(new BasicNameValuePair("remember", "1"));
        formparams.add(new BasicNameValuePair("username", JavaCIPUnknownScope.getUsername()));
        formparams.add(new BasicNameValuePair("password", JavaCIPUnknownScope.getPassword()));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        httppost.setEntity(entity);
        HttpResponse httpresponse = httpclient.execute(httppost);
        NULogger.getLogger().info("Getting cookies........");
        Iterator<Cookie> it = httpclient.getCookieStore().getCookies().iterator();
        Cookie escookie = null;
        while (it.hasNext()) {
            escookie = it.next();
            if (escookie.getName().equalsIgnoreCase("ssal")) {
                JavaCIPUnknownScope.ssalcookie = escookie.getName() + "=" + escookie.getValue();
                NULogger.getLogger().info(JavaCIPUnknownScope.ssalcookie);
                JavaCIPUnknownScope.loginsuccessful = true;
            }
        }
        if (JavaCIPUnknownScope.loginsuccessful) {
            JavaCIPUnknownScope.username = JavaCIPUnknownScope.getUsername();
            JavaCIPUnknownScope.password = JavaCIPUnknownScope.getPassword();
            NULogger.getLogger().info("SendSpace login success :)");
        } else {
            NULogger.getLogger().info("SendSpace login failed :(");
            JavaCIPUnknownScope.loginsuccessful = false;
            JavaCIPUnknownScope.username = "";
            JavaCIPUnknownScope.password = "";
            JOptionPane.showMessageDialog(NeembuuUploader.getInstance(), "<html><b>" + JavaCIPUnknownScope.HOSTNAME + "</b> " + TranslationProvider.get("neembuuuploader.accounts.loginerror") + "</html>", JavaCIPUnknownScope.HOSTNAME, JOptionPane.WARNING_MESSAGE);
            AccountsManager.getInstance().setVisible(true);
        }
    }
}
