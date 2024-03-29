


class c6781681 {

    public String expandTemplate(String target) throws IORuntimeException, HttpRuntimeException {
        connect();
        try {
            HttpGet request = new HttpGet(contextPath + target);
            HttpResponse response = httpexecutor.execute(request, conn);
            TolvenLogger.info("Response: " + response.getStatusLine(), TemplateGen.class);
            disconnect();
            return EntityUtils.toString(response.getEntity());
        } finally {
            disconnect();
        }
    }

}
