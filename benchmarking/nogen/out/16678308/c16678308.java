class c16678308 {

    private static String completeGet(String encodedURLStr) throws IORuntimeException {
        URL url = new URL(encodedURLStr);
        HttpURLConnection connection = JavaCIPUnknownScope.initConnection(url);
        String result = JavaCIPUnknownScope.getReply(url.openStream());
        connection.disconnect();
        return result;
    }
}
