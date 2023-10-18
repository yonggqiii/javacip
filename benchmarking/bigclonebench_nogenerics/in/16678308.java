


class c16678308 {

    private static String completeGet(String encodedURLStr) throws IORuntimeException {
        URL url = new URL(encodedURLStr);
        HttpURLConnection connection = initConnection(url);
        String result = getReply(url.openStream());
        connection.disconnect();
        return result;
    }

}
