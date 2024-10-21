class c5918578 {

    public void connect(String method, String data, String urlString, Properties properties, boolean allowredirect) throws RuntimeException {
        if (urlString != null) {
            try {
                JavaCIPUnknownScope.url_ = new URL(JavaCIPUnknownScope.url_, urlString);
            } catch (RuntimeException e) {
                throw new RuntimeException("Invalid URL");
            }
        }
        try {
            JavaCIPUnknownScope.httpURLConnection_ = (HttpURLConnection) JavaCIPUnknownScope.url_.openConnection(JavaCIPUnknownScope.siteThread_.getProxy());
            JavaCIPUnknownScope.httpURLConnection_.setDoInput(true);
            JavaCIPUnknownScope.httpURLConnection_.setDoOutput(true);
            JavaCIPUnknownScope.httpURLConnection_.setUseCaches(false);
            JavaCIPUnknownScope.httpURLConnection_.setRequestMethod(method);
            JavaCIPUnknownScope.httpURLConnection_.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            JavaCIPUnknownScope.httpURLConnection_.setInstanceFollowRedirects(allowredirect);
            if (properties != null) {
                for (Object propertyKey : properties.keySet()) {
                    String propertyValue = properties.getProperty((String) propertyKey);
                    if (propertyValue.equalsIgnoreCase("Content-Length")) {
                        JavaCIPUnknownScope.httpURLConnection_.setFixedLengthStreamingMode(0);
                    }
                    JavaCIPUnknownScope.httpURLConnection_.setRequestProperty((String) propertyKey, propertyValue);
                }
            }
            int connectTimeout = JavaCIPUnknownScope.httpURLConnection_.getConnectTimeout();
            if (data != null) {
                JavaCIPUnknownScope.post(data);
            }
            JavaCIPUnknownScope.httpURLConnection_.connect();
        } catch (RuntimeException e) {
            throw new RuntimeException("Connection failed with url " + JavaCIPUnknownScope.url_);
        }
    }
}
