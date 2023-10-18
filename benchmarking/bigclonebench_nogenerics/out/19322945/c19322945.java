class c19322945 {

    public void test_lookupType_FullSearch_CaseSensivity() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/lookupType/moRO");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("[{\"itemTypeID\":19724,\"itemCategoryID\":6,\"name\":\"Moros\"},{\"itemTypeID\":19725,\"itemCategoryID\":9,\"name\":\"Moros Blueprint\"}]"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
    }
}
