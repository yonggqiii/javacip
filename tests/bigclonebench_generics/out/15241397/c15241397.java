class c15241397 {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rewrittenQueryString = URLDecoder.decode(request.getRequestURI(), "UTF-8").replaceFirst("^.*?\\/(id:.*)\\/.*?$", "$1");
        JavaCIPUnknownScope.logger.debug("rewrittenQueryString: " + rewrittenQueryString);
        URL rewrittenUrl = new URL(JavaCIPUnknownScope.fedoraUrl + rewrittenQueryString);
        JavaCIPUnknownScope.logger.debug("rewrittenUrl: " + rewrittenUrl.getProtocol() + "://" + rewrittenUrl.getHost() + ":" + rewrittenUrl.getPort() + rewrittenUrl.getFile());
        HttpURLConnection httpURLConnection = (HttpURLConnection) rewrittenUrl.openConnection();
        HttpURLConnection.setFollowRedirects(false);
        httpURLConnection.connect();
        response.setStatus(httpURLConnection.getResponseCode());
        JavaCIPUnknownScope.logger.debug("[status=" + httpURLConnection.getResponseCode() + "]");
        JavaCIPUnknownScope.logger.debug("[headers]");
        for (Entry<String, List<String>> header : httpURLConnection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                for (String value : header.getValue()) {
                    if (value != null) {
                        JavaCIPUnknownScope.logger.debug(header.getKey() + ": " + value);
                        if (!header.getKey().equals("Server") && !header.getKey().equals("Transfer-Encoding")) {
                            response.addHeader(header.getKey(), value);
                        }
                    }
                }
            }
        }
        JavaCIPUnknownScope.logger.debug("[/headers]");
        InputStream inputStream = httpURLConnection.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
    }
}
