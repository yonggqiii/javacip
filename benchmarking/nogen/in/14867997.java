


class c14867997 {

    public String getResponse(URL url) throws OAuthRuntimeException {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IORuntimeException e) {
            throw new OAuthRuntimeException("Error getting HTTP response", e);
        }
    }

}
