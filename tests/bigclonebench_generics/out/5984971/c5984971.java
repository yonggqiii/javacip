class c5984971 {

    public static HttpEntity sendHE(String method, String url, Map<String, String> paramMap, String encoding) throws HttpServerStatusException {
        Log.i(JavaCIPUnknownScope.TAG, "url:" + url);
        boolean bVisitOK = false;
        int tryCnt = 0;
        while (!bVisitOK && (tryCnt++ < JavaCIPUnknownScope.MAXTRYCNT)) {
            try {
                HttpRequestBase base = JavaCIPUnknownScope.getExecuteMethod(method, url, paramMap, null);
                HttpResponse response = JavaCIPUnknownScope.client.execute(base, JavaCIPUnknownScope.localContext);
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return entity;
                    }
                } else {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    throw new HttpServerStatusException(status, "");
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
        return null;
    }
}
