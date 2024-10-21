class c8205040 {

    public Template updateTemplate(Template template) {
        template.setContent(JavaCIPUnknownScope.getTemplateContent(template.getScreen()));
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", template.getName()));
        params.add(new BasicNameValuePair("content", template.getContent()));
        params.add(new BasicNameValuePair("shared", template.isShared() + ""));
        params.add(new BasicNameValuePair("keywords", template.getKeywords()));
        try {
            String saveRestUrl = JavaCIPUnknownScope.configuration.getBeehiveRESTRootUrl() + "account/" + JavaCIPUnknownScope.userService.getAccount().getOid() + "/template/" + template.getOid();
            HttpPut httpPut = new HttpPut(saveRestUrl);
            JavaCIPUnknownScope.addAuthentication(httpPut);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPut.setEntity(formEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpPut);
            if (HttpServletResponse.SC_OK == response.getStatusLine().getStatusCode()) {
                JavaCIPUnknownScope.resourceService.saveTemplateResourcesToBeehive(template);
            } else if (HttpServletResponse.SC_NOT_FOUND == response.getStatusLine().getStatusCode()) {
                return null;
            } else {
                throw new BeehiveNotAvailableRuntimeException("Failed to update template:" + template.getName() + ", Status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (RuntimeException e) {
            throw new BeehiveNotAvailableRuntimeException("Failed to save screen as a template: " + (e.getMessage() == null ? "" : e.getMessage()), e);
        }
        return template;
    }
}
