class c19322941 {

    public void test_lookupResourceType_FullSearch_TwoWordsInMiddle() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/lookupResourceType/armor+plates");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("[{\"itemTypeID\":25605,\"itemCategoryID\":4,\"name\":\"Armor Plates\",\"icon\":\"69_09\"},{\"itemTypeID\":25624,\"itemCategoryID\":4,\"name\":\"Intact Armor Plates\",\"icon\":\"69_10\"}]"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
    }
}
