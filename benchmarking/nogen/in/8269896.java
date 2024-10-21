


class c8269896 {

    private String postData(String requestUrl, String atom) throws AuthenticationRuntimeException, IORuntimeException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String header;
        try {
            header = oauthAuthenticator.getHttpAuthorizationHeader(url.toString(), "POST", profile.getOAuthToken(), profile.getOAuthTokenSecret());
        } catch (OAuthRuntimeException e) {
            throw new AuthenticationRuntimeException(e);
        }
        conn.setRequestProperty("Authorization", header);
        conn.setRequestProperty("Content-Type", "application/atom+xml");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(atom);
        writer.close();
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
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new IORuntimeException(conn.getResponseCode() + " " + conn.getResponseMessage() + "\n" + data);
        }
        return data.toString();
    }

}
