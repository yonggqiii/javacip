class c19322906 {

    public void test_baseMaterialsForTypeID_NonExistingID() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/baseMaterialsForTypeID/1234567890");
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
