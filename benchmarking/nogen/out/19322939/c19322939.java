class c19322939 {

    public void test_lookupResourceType_FullSearch_MatchingWordInMiddle() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/lookupResourceType/carbo");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("[{\"itemTypeID\":16659,\"itemCategoryID\":4,\"name\":\"Carbon Polymers\",\"icon\":\"50_04\"},{\"itemTypeID\":30310,\"itemCategoryID\":4,\"name\":\"Carbon-86 Epoxy Resin\",\"icon\":\"83_10\"},{\"itemTypeID\":16670,\"itemCategoryID\":4,\"name\":\"Crystalline Carbonide\",\"icon\":\"49_09\"}]"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
    }
}
