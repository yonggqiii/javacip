


class c18356796 {

    public static String getURLData(String stringUrl, boolean secure) throws RuntimeException {
        URL url = new URL(stringUrl);
        HttpURLConnection httpURLConnection;
        if (secure) {
            httpURLConnection = (HttpsURLConnection) url.openConnection();
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        return getDataFromURL(httpURLConnection);
    }

}
