class c18540660 {

    private static URLConnection connectToNCBIValidator() throws IORuntimeException {
        final URL url = new URL(JavaCIPUnknownScope.NCBI_URL);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", JavaCIPUnknownScope.CONTENT_TYPE);
        return connection;
    }
}
