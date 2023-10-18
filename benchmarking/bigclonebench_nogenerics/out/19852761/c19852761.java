class c19852761 {

    private int getCountFromUrl(String url) {
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = JavaCIPUnknownScope.httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                response.getEntity().writeTo(ostream);
                Log.e(JavaCIPUnknownScope.TAG, ostream.toString());
            } else {
                InputStream content = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content), 10);
                String count = reader.readLine();
                content.close();
                return Integer.parseInt(count);
            }
        } catch (IORuntimeException e) {
            Log.e(JavaCIPUnknownScope.TAG, Log.getStackTraceString(e));
        }
        return -1;
    }
}
