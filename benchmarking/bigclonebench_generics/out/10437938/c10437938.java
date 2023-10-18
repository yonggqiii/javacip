class c10437938 {

    protected Document loadDocument() throws MalformedURLException, DocumentException, IOException {
        if (JavaCIPUnknownScope.jiraFilterURL.startsWith("file")) {
            URL url = JavaCIPUnknownScope.getSourceURL();
            return JavaCIPUnknownScope.parseDocument(url);
        } else {
            HttpClient httpClient = new DefaultHttpClient();
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("os_username", JavaCIPUnknownScope.jiraUser));
            formparams.add(new BasicNameValuePair("os_password", JavaCIPUnknownScope.jiraPassword));
            formparams.add(new BasicNameValuePair("os_cookie", "true"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost post = new HttpPost(JavaCIPUnknownScope.getJiraRootUrl() + "/secure/login.jsp");
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            response.getEntity().consumeContent();
            String url_str = StringEscapeUtils.unescapeXml(JavaCIPUnknownScope.jiraFilterURL);
            HttpGet get = new HttpGet(url_str);
            response = httpClient.execute(get);
            return JavaCIPUnknownScope.parseDocument(response.getEntity().getContent());
        }
    }
}
