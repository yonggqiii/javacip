class c18708933 {

    public APIResponse delete(String id) throws RuntimeException {
        APIResponse response = new APIResponse();
        JavaCIPUnknownScope.connection = (HttpURLConnection) new URL(JavaCIPUnknownScope.url + "/api/user/delete/" + id).openConnection();
        JavaCIPUnknownScope.connection.setRequestMethod("DELETE");
        JavaCIPUnknownScope.connection.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
        JavaCIPUnknownScope.connection.connect();
        if (JavaCIPUnknownScope.connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            response.setDone(true);
            response.setMessage("User Deleted!");
        } else {
            response.setDone(false);
            response.setMessage("Delete User Error Code: Http (" + JavaCIPUnknownScope.connection.getResponseCode() + ")");
        }
        JavaCIPUnknownScope.connection.disconnect();
        return response;
    }
}
