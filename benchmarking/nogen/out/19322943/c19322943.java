class c19322943 {

    public void test_lookupType_NonExistingName() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/lookupType/blah-blah");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("[]"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><rowset/>"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/xml; charset=utf-8"));
    }
}
