


class c1766801 {

    public void update() {
        Authenticator.setDefault(new MyAuthenticator());
        URL url = null;
        try {
            url = new URL("http://trade.gigabass.de/update/update.php");
        } catch (MalformedURLRuntimeException e) {
            handleRuntimeException(e);
            return;
        }
        URLConnection conn;
        try {
            conn = url.openConnection();
        } catch (IORuntimeException e) {
            handleRuntimeException(e);
            return;
        }
        conn.setDoOutput(true);
        OutputStreamWriter wr = null;
        try {
            wr = new OutputStreamWriter(conn.getOutputStream());
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        try {
            wr.write("sql=" + URLEncoder.encode(sql, "UTF-8") + "\n");
            wr.flush();
        } catch (IORuntimeException e) {
            handleRuntimeException(e);
        }
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
            }
        } catch (IORuntimeException e) {
            handleRuntimeException(e);
        }
        try {
            wr.close();
        } catch (IORuntimeException e) {
            handleRuntimeException(e);
        }
        try {
            rd.close();
        } catch (IORuntimeException e) {
            handleRuntimeException(e);
        }
    }

}
