class c14208906 {

    private void logoutUser(String session) {
        try {
            String data = URLEncoder.encode("SESSION", "UTF-8") + "=" + URLEncoder.encode("" + session, "UTF-8");
            if (JavaCIPUnknownScope._log != null)
                JavaCIPUnknownScope._log.error("Voice: logoutUser = " + JavaCIPUnknownScope.m_strUrl + "LogoutUserServlet&" + data);
            URL url = new URL(JavaCIPUnknownScope.m_strUrl + "LogoutUserServlet");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            wr.close();
            rd.close();
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope._log != null)
                JavaCIPUnknownScope._log.error("Voice error : " + e);
        }
    }
}
