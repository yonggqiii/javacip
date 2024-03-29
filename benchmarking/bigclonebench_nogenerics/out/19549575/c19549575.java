class c19549575 {

    protected InputStream callApiGet(String apiUrl, int expected) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            if (ApplicationConstants.CONNECT_TIMEOUT > -1) {
                request.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT);
            }
            if (ApplicationConstants.READ_TIMEOUT > -1) {
                request.setReadTimeout(ApplicationConstants.READ_TIMEOUT);
            }
            for (String headerName : JavaCIPUnknownScope.requestHeaders.keySet()) {
                request.setRequestProperty(headerName, JavaCIPUnknownScope.requestHeaders.get(headerName));
            }
            request.connect();
            if (request.getResponseCode() != expected) {
                throw new BingMapsRuntimeException(JavaCIPUnknownScope.convertStreamToString(request.getErrorStream()));
            } else {
                return JavaCIPUnknownScope.getWrappedInputStream(request.getInputStream(), JavaCIPUnknownScope.GZIP_ENCODING.equalsIgnoreCase(request.getContentEncoding()));
            }
        } catch (IORuntimeException e) {
            throw new BingMapsRuntimeException(e);
        }
    }
}
