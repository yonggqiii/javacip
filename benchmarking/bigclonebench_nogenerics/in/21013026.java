


class c21013026 {

    private InputStream getPageStream(String query) throws MalformedURLRuntimeException, IORuntimeException {
        URL url = new URL(baseUrl + query + "&rhtml=no");
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream in = connection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        return bis;
    }

}
