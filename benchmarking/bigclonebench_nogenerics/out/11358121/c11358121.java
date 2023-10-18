class c11358121 {

    public void run() {
        try {
            JavaCIPUnknownScope.putEvent(new DebugEvent("about to place HTTP request"));
            HttpGet req = new HttpGet(JavaCIPUnknownScope.requestURL);
            req.addHeader("Connection", "close");
            HttpResponse httpResponse = JavaCIPUnknownScope.httpClient.execute(req);
            JavaCIPUnknownScope.putEvent(new DebugEvent("got response to HTTP request"));
            JavaCIPUnknownScope.nonSipPort.input(new Integer(httpResponse.getStatusLine().getStatusCode()));
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                if (in != null)
                    in.close();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
