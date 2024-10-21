class c14501401 {

    private void checkServerAccess() throws IORuntimeException {
        URL url = new URL("https://testnetbeans.org/bugzilla/index.cgi");
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
        } catch (IORuntimeException exc) {
            JavaCIPUnknownScope.disableMessage = "Bugzilla is not accessible";
        }
        url = new URL(BugzillaConnector.SERVER_URL);
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
        } catch (IORuntimeException exc) {
            JavaCIPUnknownScope.disableMessage = "Bugzilla Service is not accessible";
        }
    }
}
