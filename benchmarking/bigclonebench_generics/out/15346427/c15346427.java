class c15346427 {

    private ArrayList loadResults(String text, String index, int from) {
        JavaCIPUnknownScope.loadingMore = true;
        JSONObject job = new JSONObject();
        ArrayList al = new ArrayList();
        try {
            String req = job.put("OperationId", "2").toString();
            InputStream is = null;
            String result = "";
            JSONObject jArray = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.1.4:8080/newgenlibctxt/CarbonServlet");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("OperationId", "2"));
                nameValuePairs.add(new BasicNameValuePair("Text", text));
                nameValuePairs.add(new BasicNameValuePair("Index", index));
                nameValuePairs.add(new BasicNameValuePair("From", from + ""));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jobres = new JSONObject(result);
                JSONArray jarr = jobres.getJSONArray("Records");
                for (int i = 0; i < jarr.length(); i++) {
                    String title = jarr.getJSONObject(i).getString("title");
                    String author = jarr.getJSONObject(i).getString("author");
                    String[] id = new String[2];
                    id[0] = jarr.getJSONObject(i).getString("cataloguerecordid");
                    id[1] = jarr.getJSONObject(i).getString("ownerlibraryid");
                    JavaCIPUnknownScope.alOnlyIds.add(id);
                    al.add(Html.fromHtml("<html><body><b>" + title + "</b><br>by " + author + "</body></html>"));
                }
            } catch (JSONRuntimeException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException exp) {
            exp.printStackTrace();
        }
        JavaCIPUnknownScope.loadingMore = false;
        return al;
    }
}
