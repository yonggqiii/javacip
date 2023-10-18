class c21868018 {

    public void process() throws Exception {
        String searchXML = FileUtils.readFileToString(new File(JavaCIPUnknownScope.getSearchRequestRelativeFilePath()));
        Map<String, String> parametersMap = new HashMap<String, String>();
        parametersMap.put("searchXML", searchXML);
        String proxyHost = null;
        int proxyPort = -1;
        String serverUserName = null;
        String serverUserPassword = null;
        FileOutputStream fos = null;
        if (JavaCIPUnknownScope.getUseProxy()) {
            serverUserName = JavaCIPUnknownScope.getServerUserName();
            serverUserPassword = JavaCIPUnknownScope.getServerUserPassword();
        }
        if (JavaCIPUnknownScope.getUseProxy()) {
            proxyHost = JavaCIPUnknownScope.getProxyHost();
            proxyPort = JavaCIPUnknownScope.getProxyPort();
        }
        try {
            InputStream responseInputStream = URLUtils.getHttpResponse(JavaCIPUnknownScope.getSearchBaseURL(), serverUserName, serverUserPassword, URLUtils.HTTP_POST_METHOD, proxyHost, proxyPort, parametersMap, -1);
            fos = new FileOutputStream(JavaCIPUnknownScope.getSearchResponseRelativeFilePath());
            IOUtils.copyLarge(responseInputStream, fos);
        } finally {
            if (null != fos) {
                fos.flush();
                fos.close();
            }
        }
    }
}
