class c1485384 {

    private void runGetVendorProfile() {
        DataStorage.clearVendorProfile();
        GenericUrl url = new GoogleUrl(EnterpriseMarketplaceUrl.generateVendorProfileUrl());
        VendorProfile vendorProfile = null;
        try {
            HttpRequest request = JavaCIPUnknownScope.requestFactory.buildGetRequest(url);
            request.addParser(JavaCIPUnknownScope.jsonHttpParser);
            request.readTimeout = JavaCIPUnknownScope.readTimeout;
            HttpResponse response = request.execute();
            vendorProfile = response.parseAs(VendorProfile.class);
            if (vendorProfile != null && vendorProfile.vendorId != null && vendorProfile.email != null && !StringUtilities.isEmpty(vendorProfile.email)) {
                DataStorage.setVendorProfile(vendorProfile);
                JavaCIPUnknownScope.operationStatus = true;
            }
            response.getContent().close();
        } catch (IORuntimeException e) {
            AppsMarketplacePluginLog.logError(e);
        }
    }
}
