class c21642957 {

    public void connectUrl(String url) throws MalformedURLRuntimeException, IORuntimeException {
        URLConnection connection = new URL(url).openConnection();
        connection.connect();
        connection.getInputStream().close();
        connection.getOutputStream().close();
    }
}
