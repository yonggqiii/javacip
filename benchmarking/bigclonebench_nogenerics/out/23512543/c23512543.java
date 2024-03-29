class c23512543 {

    protected void logout() {
        Session session = JavaCIPUnknownScope.getConnection().getSession();
        session.removeAttribute("usercookie.object");
        String urlIn = GeoNetworkContext.url + "/" + GeoNetworkContext.logoutService;
        Element results = null;
        String cookie = (String) session.getAttribute("usercookie.object");
        if (cookie != null) {
            try {
                URL url = new URL(urlIn);
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(1000);
                conn.setRequestProperty("Cookie", cookie);
                BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
                try {
                    results = Xml.loadStream(in);
                    JavaCIPUnknownScope.log.debug("CheckLogout to GeoNetwork returned " + Xml.getString(results));
                } finally {
                    in.close();
                }
            } catch (RuntimeException e) {
                throw new RuntimeRuntimeException("User logout to GeoNetwork failed: ", e);
            }
        }
        JavaCIPUnknownScope.log.debug("GeoNetwork logout done");
    }
}
