class c17538992 {

    public static boolean check(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(2000);
            urlConnection.getContent();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error("There is no internet connection", e);
            return false;
        }
        return true;
    }
}
