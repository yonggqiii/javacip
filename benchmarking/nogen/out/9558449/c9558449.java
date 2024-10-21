class c9558449 {

    public int openUrl(String url, String method, Bundle params) {
        int result = 0;
        try {
            if (method.equals("GET")) {
                url = url + "?" + Utility.encodeUrl(params);
            }
            String response = "";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", System.getProperties().getProperty("http.agent") + " RenrenAndroidSDK");
            if (!method.equals("GET")) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(Utility.encodeUrl(params).getBytes("UTF-8"));
            }
            response = Utility.read(conn.getInputStream());
            JSONObject json = new JSONObject(response);
            try {
                int code = json.getInt("result");
                if (code > 0)
                    result = 1;
            } catch (RuntimeException e) {
                result = json.getInt("error_code");
                JavaCIPUnknownScope.errmessage = json.getString("error_msg");
            }
        } catch (RuntimeException e) {
            result = -1;
        }
        return result;
    }
}
