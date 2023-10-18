class c20596004 {

    public APIResponse create(Item item) throws RuntimeException {
        APIResponse response = new APIResponse();
        JavaCIPUnknownScope.connection = (HttpURLConnection) new URL(JavaCIPUnknownScope.url + "/api/item/create").openConnection();
        JavaCIPUnknownScope.connection.setDoOutput(true);
        JavaCIPUnknownScope.connection.setRequestMethod("POST");
        JavaCIPUnknownScope.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        JavaCIPUnknownScope.connection.setUseCaches(false);
        JavaCIPUnknownScope.connection.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
        JavaCIPUnknownScope.connection.connect();
        JavaCIPUnknownScope.marshaller.marshal(item, new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), new OutputStreamWriter(JavaCIPUnknownScope.connection.getOutputStream(), "utf-8")));
        JavaCIPUnknownScope.connection.getOutputStream().flush();
        JavaCIPUnknownScope.connection.getOutputStream().close();
        if (JavaCIPUnknownScope.connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JSONObject obj = new JSONObject(new String(new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.connection.getInputStream(), "utf-8")).readLine()));
            response.setDone(true);
            response.setMessage(JavaCIPUnknownScope.unmarshaller.unmarshal(new MappedXMLStreamReader(obj, new MappedNamespaceConvention(new Configuration()))));
            JavaCIPUnknownScope.connection.getInputStream().close();
        } else {
            response.setDone(false);
            response.setMessage("Create Item Error Code: Http (" + JavaCIPUnknownScope.connection.getResponseCode() + ")");
        }
        JavaCIPUnknownScope.connection.disconnect();
        return response;
    }
}
