class c8269895 {

    private String getData(String requestUrl) throws AuthenticationRuntimeException, IORuntimeException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String header;
        try {
            header = JavaCIPUnknownScope.oauthAuthenticator.getHttpAuthorizationHeader(url.toString(), "GET", JavaCIPUnknownScope.profile.getOAuthToken(), JavaCIPUnknownScope.profile.getOAuthTokenSecret());
        } catch (OAuthRuntimeException e) {
            throw new AuthenticationRuntimeException(e);
        }
        conn.setRequestProperty("Authorization", header);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            throw new AuthenticationRuntimeException();
        }
        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        char[] buffer = new char[1024];
        int bytesRead = 0;
        StringBuilder data = new StringBuilder();
        while ((bytesRead = reader.read(buffer)) != -1) {
            data.append(buffer, 0, bytesRead);
        }
        reader.close();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IORuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage() + "\n" + data);
        }
        return data.toString();
    }
}
