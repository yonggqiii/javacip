class c17407851 {

    private String _doPost(final String urlStr, final Map<String, String> params) {
        String paramsStr = "";
        for (String key : params.keySet()) {
            try {
                paramsStr += URLEncoder.encode(key, JavaCIPUnknownScope.ENCODING) + "=" + URLEncoder.encode(params.get(key), JavaCIPUnknownScope.ENCODING) + "&";
            } catch (UnsupportedEncodingException e) {
                JavaCIPUnknownScope.s_logger.debug("UnsupportedEncodingException caught. Trying to encode: " + key + " and " + params.get(key));
                return null;
            }
        }
        if (paramsStr.length() == 0) {
            JavaCIPUnknownScope.s_logger.debug("POST will not complete, no parameters specified.");
            return null;
        }
        JavaCIPUnknownScope.s_logger.debug("POST to server will be done with the following parameters: " + paramsStr);
        HttpURLConnection connection = null;
        String responseStr = null;
        try {
            connection = (HttpURLConnection) (new URL(urlStr)).openConnection();
            connection.setRequestMethod(JavaCIPUnknownScope.REQUEST_METHOD);
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.write(paramsStr.getBytes());
            dos.flush();
            dos.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            responseStr = response.toString();
        } catch (ProtocolException e) {
            JavaCIPUnknownScope.s_logger.debug("ProtocolException caught. Unable to execute POST.");
        } catch (MalformedURLException e) {
            JavaCIPUnknownScope.s_logger.debug("MalformedURLException caught. Unexpected. Url is: " + urlStr);
        } catch (IOException e) {
            JavaCIPUnknownScope.s_logger.debug("IOException caught. Unable to execute POST.");
        }
        return responseStr;
    }
}
