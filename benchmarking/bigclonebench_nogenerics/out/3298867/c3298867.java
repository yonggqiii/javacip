class c3298867 {

    public APIResponse delete(String id) throws RuntimeException {
        APIResponse response = new APIResponse();
        JavaCIPUnknownScope.connection = (HttpURLConnection) new URL(JavaCIPUnknownScope.url + "/api/transaction/delete/" + id).openConnection();
        JavaCIPUnknownScope.connection.setRequestMethod("DELETE");
        JavaCIPUnknownScope.connection.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
        JavaCIPUnknownScope.connection.connect();
        if (JavaCIPUnknownScope.connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            response.setDone(true);
            response.setMessage("Transaction Deleted!");
        } else {
            response.setDone(false);
            response.setMessage("Delete Transaction Error Code: Http (" + JavaCIPUnknownScope.connection.getResponseCode() + ")");
        }
        JavaCIPUnknownScope.connection.disconnect();
        return response;
    }
}
