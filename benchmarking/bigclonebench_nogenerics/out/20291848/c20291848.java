class c20291848 {

    public void run() {
        if (!JavaCIPUnknownScope.shouldLogin) {
            JavaCIPUnknownScope.u.p("skipping the auto-login");
            return;
        }
        try {
            JavaCIPUnknownScope.u.p("logging in to the server");
            String query = "hostname=blahfoo2.com" + "&osname=" + URLEncoder.encode(System.getProperty("os.name"), "UTF-8") + "&javaver=" + URLEncoder.encode(System.getProperty("java.runtime.version"), "UTF-8") + "&timezone=" + URLEncoder.encode(TimeZone.getDefault().getID(), "UTF-8");
            JavaCIPUnknownScope.u.p("unencoded query: \n" + query);
            String login_url = "http://joshy.org:8088/org.glossitopeTracker/login.jsp?";
            String url = login_url + query;
            JavaCIPUnknownScope.u.p("final encoded url = \n" + url);
            InputStream in = new URL(url).openStream();
            byte[] buf = new byte[256];
            while (true) {
                int n = in.read(buf);
                if (n == -1)
                    break;
                for (int i = 0; i < n; i++) {
                }
            }
        } catch (MalformedURLRuntimeException ex) {
            ex.printStackTrace();
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
        }
    }
}
