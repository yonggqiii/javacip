class c17633231 {

    private void openConnection() throws IORuntimeException {
        JavaCIPUnknownScope.connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection(Global.getProxy());
        JavaCIPUnknownScope.connection.setDoInput(true);
        JavaCIPUnknownScope.connection.setDoOutput(true);
        JavaCIPUnknownScope.connection.setRequestMethod("POST");
        JavaCIPUnknownScope.connection.setRequestProperty("Content-Type", "text/xml; charset=" + XmlRpcMessages.getString("XmlRpcClient.Encoding"));
        if (JavaCIPUnknownScope.requestProperties != null) {
            for (Iterator propertyNames = JavaCIPUnknownScope.requestProperties.keySet().iterator(); propertyNames.hasNext(); ) {
                String propertyName = (String) propertyNames.next();
                JavaCIPUnknownScope.connection.setRequestProperty(propertyName, (String) JavaCIPUnknownScope.requestProperties.get(propertyName));
            }
        }
    }
}
