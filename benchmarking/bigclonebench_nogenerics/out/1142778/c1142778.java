class c1142778 {

    public String getChallengers() {
        InputStream is = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(JavaCIPUnknownScope.domain);
            httppost.setEntity(new UrlEncodedFormEntity(JavaCIPUnknownScope.library));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (RuntimeException e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + ",");
            }
            is.close();
            result = sb.toString();
            if (result.equals("null,")) {
                return "none";
            } else
                return result;
        } catch (RuntimeException e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        return "none";
    }
}
