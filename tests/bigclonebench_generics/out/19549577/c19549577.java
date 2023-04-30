class c19549577 {

    protected InputStream callApiPost(String apiUrl, Map<String, List<String>> parameters, int expected) {
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
            request.setRequestMethod("POST");
            request.setDoOutput(true);
            PrintStream out = new PrintStream(new BufferedOutputStream(request.getOutputStream()));
            out.print(JavaCIPUnknownScope.getParametersString(parameters));
            out.flush();
            out.close();
            request.connect();
            if (request.getResponseCode() != expected) {
                throw new BingMapsException(JavaCIPUnknownScope.convertStreamToString(request.getErrorStream()));
            } else {
                return JavaCIPUnknownScope.getWrappedInputStream(request.getInputStream(), GZIP_ENCODING.equalsIgnoreCase(request.getContentEncoding()));
            }
        } catch (IOException e) {
            throw new BingMapsException(e);
        }
    }
}
