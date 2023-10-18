class c19322913 {

    public void test_blueprintTypeByTypeName() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.baseUrl + "/blueprintTypeByTypeName/Obelisk+Blueprint");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("{\"blueprintTypeID\":20188,\"blueprintTypeName\":\"Obelisk Blueprint\",\"productTypeID\":20187,\"productTypeName\":\"Obelisk\",\"productCategoryID\":6,\"techLevel\":1,\"productionTime\":1280000,\"researchProductivityTime\":7680000,\"researchMaterialTime\":5120000,\"researchCopyTime\":2560000,\"researchTechTime\":500000,\"productivityModifier\":256000,\"wasteFactor\":10,\"maxProductionLimit\":1,\"productVolume\":\"17550000\",\"productPortionSize\":1,\"dumpVersion\":\"cru16\"}"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><invBlueprintTypeDto><blueprintTypeID>20188</blueprintTypeID><blueprintTypeName>Obelisk Blueprint</blueprintTypeName><dumpVersion>cru16</dumpVersion><maxProductionLimit>1</maxProductionLimit><productCategoryID>6</productCategoryID><productPortionSize>1</productPortionSize><productTypeID>20187</productTypeID><productTypeName>Obelisk</productTypeName><productVolume>17550000</productVolume><productionTime>1280000</productionTime><productivityModifier>256000</productivityModifier><researchCopyTime>2560000</researchCopyTime><researchMaterialTime>5120000</researchMaterialTime><researchProductivityTime>7680000</researchProductivityTime><researchTechTime>500000</researchTechTime><techLevel>1</techLevel><wasteFactor>10</wasteFactor></invBlueprintTypeDto>"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/xml; charset=utf-8"));
        url = new URL(JavaCIPUnknownScope.baseUrl + "/blueprintTypeByTypeName/Obelisk%20Blueprint");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("{\"blueprintTypeID\":20188,\"blueprintTypeName\":\"Obelisk Blueprint\",\"productTypeID\":20187,\"productTypeName\":\"Obelisk\",\"productCategoryID\":6,\"techLevel\":1,\"productionTime\":1280000,\"researchProductivityTime\":7680000,\"researchMaterialTime\":5120000,\"researchCopyTime\":2560000,\"researchTechTime\":500000,\"productivityModifier\":256000,\"wasteFactor\":10,\"maxProductionLimit\":1,\"productVolume\":\"17550000\",\"productPortionSize\":1,\"dumpVersion\":\"cru16\"}"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/json; charset=utf-8"));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(200));
        JavaCIPUnknownScope.assertThat(JavaCIPUnknownScope.getResponse(connection), JavaCIPUnknownScope.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><invBlueprintTypeDto><blueprintTypeID>20188</blueprintTypeID><blueprintTypeName>Obelisk Blueprint</blueprintTypeName><dumpVersion>cru16</dumpVersion><maxProductionLimit>1</maxProductionLimit><productCategoryID>6</productCategoryID><productPortionSize>1</productPortionSize><productTypeID>20187</productTypeID><productTypeName>Obelisk</productTypeName><productVolume>17550000</productVolume><productionTime>1280000</productionTime><productivityModifier>256000</productivityModifier><researchCopyTime>2560000</researchCopyTime><researchMaterialTime>5120000</researchMaterialTime><researchProductivityTime>7680000</researchProductivityTime><researchTechTime>500000</researchTechTime><techLevel>1</techLevel><wasteFactor>10</wasteFactor></invBlueprintTypeDto>"));
        JavaCIPUnknownScope.assertThat(connection.getHeaderField("Content-Type"), JavaCIPUnknownScope.equalTo("application/xml; charset=utf-8"));
        url = new URL(JavaCIPUnknownScope.baseUrl + "/blueprintTypeByTypeName/Obelisk Blueprint");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(400));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        JavaCIPUnknownScope.assertThat(connection.getResponseCode(), JavaCIPUnknownScope.equalTo(400));
    }
}
