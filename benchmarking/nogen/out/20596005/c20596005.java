class c20596005 {

    public APIResponse delete(String id) throws RuntimeException {
        APIResponse response = new APIResponse();
        JavaCIPUnknownScope.connection = (HttpURLConnection) new URL(JavaCIPUnknownScope.url + "/api/item/delete/" + id).openConnection();
        JavaCIPUnknownScope.connection.setRequestMethod("DELETE");
        JavaCIPUnknownScope.connection.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
        JavaCIPUnknownScope.connection.connect();
        if (JavaCIPUnknownScope.connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            response.setDone(true);
            response.setMessage("Item Deleted!");
        } else {
            response.setDone(false);
            response.setMessage("Delete Item Error Code: Http (" + JavaCIPUnknownScope.connection.getResponseCode() + ")");
        }
        JavaCIPUnknownScope.connection.disconnect();
        return response;
    }
}
