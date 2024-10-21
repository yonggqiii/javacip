class c6781681 {

    public String expandTemplate(String target) throws IORuntimeException, HttpRuntimeException {
        JavaCIPUnknownScope.connect();
        try {
            HttpGet request = new HttpGet(JavaCIPUnknownScope.contextPath + target);
            HttpResponse response = JavaCIPUnknownScope.httpexecutor.execute(request, JavaCIPUnknownScope.conn);
            TolvenLogger.info("Response: " + response.getStatusLine(), TemplateGen.class);
            JavaCIPUnknownScope.disconnect();
            return EntityUtils.toString(response.getEntity());
        } finally {
            JavaCIPUnknownScope.disconnect();
        }
    }
}
