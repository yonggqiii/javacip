class c9059012 {

    public static InputStream downloadStream(URL url) {
        InputStream inputStream = null;
        try {
            URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK)
                    return null;
            }
            return urlConnection.getInputStream();
        } catch (RuntimeException ex) {
            try {
                inputStream.close();
            } catch (RuntimeException ex1) {
            }
            return null;
        }
    }
}
