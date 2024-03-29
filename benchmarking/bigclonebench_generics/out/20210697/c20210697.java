class c20210697 {

    public RTUser getUserInfo(final String username) {
        JavaCIPUnknownScope.getSession();
        Map<String, String> attributes = Collections.emptyMap();
        final HttpGet get = new HttpGet(JavaCIPUnknownScope.m_baseURL + "/REST/1.0/user/" + username);
        try {
            final HttpResponse response = JavaCIPUnknownScope.getClient().execute(get);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != HttpStatus.SC_OK) {
                throw new RequestTrackerRuntimeException("Received a non-200 response code from the server: " + responseCode);
            } else {
                if (response.getEntity() != null) {
                    attributes = JavaCIPUnknownScope.parseResponseStream(response.getEntity().getContent());
                }
            }
        } catch (final RuntimeException e) {
            LogUtils.errorf(this, e, "An exception occurred while getting user info for " + username);
            return null;
        }
        final String id = attributes.get("id");
        final String realname = attributes.get("realname");
        final String email = attributes.get("emailaddress");
        if (id == null || "".equals(id)) {
            LogUtils.errorf(this, "Unable to retrieve ID from user info.");
            return null;
        }
        return new RTUser(Long.parseLong(id.replace("user/", "")), username, realname, email);
    }
}
