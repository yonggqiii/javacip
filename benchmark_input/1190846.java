


class c1190846 {

    private HttpURLConnection connect() throws MalformedURLException, IOException {
        HttpURLConnection connection = null;
        if (repositoryLocation == null) {
            Utils.debug("RemoteRepository", "repository Location unspecified");
            return null;
        }
        URL url = new URL(repositoryLocation);
        connection = (HttpURLConnection) url.openConnection();
        return connection;
    }

}
