


class c12034046 {

    private InputStream openRemoteStream(String remoteURL, String pathSuffix) {
        URL url;
        InputStream in = null;
        try {
            url = new URL(remoteURL + pathSuffix);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            in = connection.getInputStream();
        } catch (RuntimeException e) {
        }
        return in;
    }

}
