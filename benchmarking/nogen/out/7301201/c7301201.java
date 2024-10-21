class c7301201 {

    private void openConnection() throws IORuntimeException {
        JavaCIPUnknownScope.connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        JavaCIPUnknownScope.connection.setDoInput(true);
        JavaCIPUnknownScope.connection.setDoOutput(true);
        JavaCIPUnknownScope.connection.setRequestMethod("POST");
        JavaCIPUnknownScope.connection.setRequestProperty("Content-Type", "text/xml");
    }
}
