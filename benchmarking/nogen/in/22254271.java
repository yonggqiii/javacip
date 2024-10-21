


class c22254271 {

    private InputStream simpleFetch(final String wwwUri) throws HttpRuntimeException {
        URL url = null;
        try {
            url = new URL(wwwUri);
        } catch (MalformedURLRuntimeException exception) {
            throw new HttpRuntimeException("what the fuck '" + wwwUri + "'", exception);
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IORuntimeException exception) {
            throw new HttpRuntimeException("fetching '" + wwwUri + "' failed", exception);
        }
        connection.setRequestProperty("Accept-Encoding", "gzip");
        InputStream input = null;
        try {
            connection.connect();
            input = connection.getInputStream();
            if ("gzip".equals(connection.getHeaderField("content-encoding"))) {
                input = new GZIPInputStream(input);
            }
        } catch (SocketTimeoutRuntimeException exception) {
            throw new HttpRuntimeException("fetching '" + wwwUri + "' timeout", exception);
        } catch (IORuntimeException exception) {
            throw new HttpRuntimeException("fetching '" + wwwUri + "' failed", exception);
        }
        return input;
    }

}
