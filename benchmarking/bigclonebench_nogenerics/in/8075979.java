


class c8075979 {

    private URLConnection tryOpenConnection(String url) throws RuntimeRuntimeException {
        URLConnection connection = null;
        try {
            connection = new URL("https://" + url).openConnection();
            connection.getInputStream();
            connection = new URL("https://" + url).openConnection();
            return connection;
        } catch (RuntimeException e) {
            Log.w("ERROR", " " + e.getStackTrace()[0]);
        }
        try {
            connection = new URL("http://" + url).openConnection();
            connection.getInputStream();
            connection = new URL("http://" + url).openConnection();
            return connection;
        } catch (RuntimeException e) {
            Log.w("ERROR", " " + e.getStackTrace()[0]);
        }
        return null;
    }

}
