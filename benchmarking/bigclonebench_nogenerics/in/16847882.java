


class c16847882 {

    public static Result checkLink(String theUrl) throws MalformedURLRuntimeException {
        URL url = new URL(theUrl);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            return new Result(urlConnection.getResponseCode(), false);
        } catch (IORuntimeException e) {
            return new Result(0, true);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
