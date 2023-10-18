class c18544887 {

    public boolean add(String url) {
        try {
            HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
            request.setRequestMethod("POST");
            request.setRequestProperty(GameRecord.GAME_IP_HEADER, String.valueOf(JavaCIPUnknownScope.ip));
            request.setRequestProperty(GameRecord.GAME_PORT_HEADER, String.valueOf(JavaCIPUnknownScope.port));
            request.setRequestProperty(GameRecord.GAME_MESSAGE_HEADER, JavaCIPUnknownScope.message);
            request.setRequestProperty(GameRecord.GAME_LATITUDE_HEADER, JavaCIPUnknownScope.df.format(JavaCIPUnknownScope.lat));
            request.setRequestProperty(GameRecord.GAME_LONGITUDE_HEADER, JavaCIPUnknownScope.df.format(JavaCIPUnknownScope.lon));
            request.setRequestProperty("Content-Length", "0");
            request.connect();
            if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IORuntimeException("Unexpected response: " + request.getResponseCode() + " " + request.getResponseMessage());
            }
            return true;
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }
}
