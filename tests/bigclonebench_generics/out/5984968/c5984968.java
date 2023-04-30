class c5984968 {

    public static String send(String method, String url, Map<String, String> paramMap, File file, String encoding) throws HttpServerStatusException {
        Log.i(JavaCIPUnknownScope.TAG, "url:" + url);
        boolean bVisitOK = false;
        int tryCnt = 0;
        String result = "";
        while (!bVisitOK && (tryCnt++ < JavaCIPUnknownScope.MAXTRYCNT)) {
            try {
                HttpRequestBase base = JavaCIPUnknownScope.getExecuteMethod(method, url, paramMap, file);
                HttpResponse response = JavaCIPUnknownScope.client.execute(base, JavaCIPUnknownScope.localContext);
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = JavaCIPUnknownScope.readByteStream(entity.getContent(), encoding);
                    entity.consumeContent();
                }
                if (status == 200) {
                    return result;
                } else {
                    throw new HttpServerStatusException(status, result);
                }
            } catch (HttpServerStatusException e) {
                throw e;
            } catch (IllegalStateException e) {
                bVisitOK = false;
                Log.e(JavaCIPUnknownScope.TAG, e.toString());
            } catch (IOException e) {
                bVisitOK = false;
                Log.e(JavaCIPUnknownScope.TAG, e.toString());
            }
        }
        return result;
    }
}
