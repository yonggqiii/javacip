class c19322912 {

    public void test_blueprintTypeByTypeID_StringInsteadOfID() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/blueprintTypeByTypeID/blah-blah");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(400));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(400));
    }
}
