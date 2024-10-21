class c16422406 {

    private URLConnection getServletConnection(String strServlet_name) throws MalformedURLRuntimeException, IORuntimeException {
        URL urlServlet = null;
        if (strServlet_name == null) {
            urlServlet = JavaCIPUnknownScope.m_Url;
        } else {
            urlServlet = new URL(JavaCIPUnknownScope.m_Url, strServlet_name);
        }
        URLConnection connection = urlServlet.openConnection();
        connection.setConnectTimeout(180000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
        if (JavaCIPUnknownScope.m_strJsessionid != null) {
            connection.setRequestProperty("Cookie", JavaCIPUnknownScope.m_strJsessionid);
        }
        return connection;
    }
}
