class c19322940 {

    public void test_lookupResourceType_FullSearch_TwoWords() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/lookupResourceType/alloyed+tritanium");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("[{\"itemTypeID\":25595,\"itemCategoryID\":4,\"name\":\"Alloyed Tritanium Bar\",\"icon\":\"69_11\"}]"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
    }
}
