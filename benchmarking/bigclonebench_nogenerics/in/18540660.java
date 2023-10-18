


class c18540660 {

    private static URLConnection connectToNCBIValidator() throws IORuntimeException {
        final URL url = new URL(NCBI_URL);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        return connection;
    }

}
