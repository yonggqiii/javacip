class c1263069 {

    public boolean getAuth(String content) throws IORuntimeException {
        String resp_remote;
        try {
            URL url = new URL(JavaCIPUnknownScope.remoteurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("md5sum=" + content);
            writer.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                resp_remote = response.toString();
                JavaCIPUnknownScope.wd.del();
                JavaCIPUnknownScope.wd.setKey(resp_remote);
                return true;
            } else {
                return false;
            }
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        return false;
    }
}
