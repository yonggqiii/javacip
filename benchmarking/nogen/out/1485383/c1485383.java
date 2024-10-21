class c1485383 {

    private void runGetAppListing() {
        DataStorage.clearAppListings();
        GenericUrl url = new GoogleUrl(EnterpriseMarketplaceUrl.generateAppListingUrl() + DataStorage.getVendorProfile().vendorId);
        AppListingList appListingList;
        try {
            HttpRequest request = JavaCIPUnknownScope.requestFactory.buildGetRequest(url);
            request.addParser(JavaCIPUnknownScope.jsonHttpParser);
            request.readTimeout = JavaCIPUnknownScope.readTimeout;
            HttpResponse response = request.execute();
            appListingList = response.parseAs(AppListingList.class);
            if (appListingList != null && appListingList.appListings != null) {
                JavaCIPUnknownScope.operationStatus = true;
                DataStorage.setAppListings(appListingList.appListings);
            }
            response.getContent().close();
        } catch (IORuntimeException e) {
            AppsMarketplacePluginLog.logError(e);
        }
    }
}
