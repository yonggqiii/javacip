class c3309233 {

    protected String doInBackground(String... params) {
        try {
            final HttpParams param = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(param, 30000);
            HttpConnectionParams.setSoTimeout(param, 30000);
            DefaultHttpClient client = new DefaultHttpClient(param);
            HttpPost post = new HttpPost("http://www.google.com/loc/json");
            post.setEntity(new StringEntity(params[0]));
            if (JavaCIPUnknownScope.DEBUG)
                Log.d("Location", params[0]);
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = resp.getEntity();
                String result = EntityUtils.toString(entity);
                return result;
            } else {
                if (JavaCIPUnknownScope.isFirstLocation) {
                    JavaCIPUnknownScope.requestGearsLocation(1);
                    JavaCIPUnknownScope.isFirstLocation = false;
                    return JavaCIPUnknownScope.RESULT_FIRST_FAILE;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
