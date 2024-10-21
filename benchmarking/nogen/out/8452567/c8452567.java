class c8452567 {

    private static JSONObject sendCouchRequest(HttpUriRequest request) {
        try {
            HttpResponse httpResponse = (HttpResponse) new DefaultHttpClient().execute(request);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String resultString = JavaCIPUnknownScope.convertStreamToString(instream);
                instream.close();
                JSONObject jsonResult = new JSONObject(resultString);
                return jsonResult;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
