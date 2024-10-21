class c1485385 {

    private void runUpdateAppListing() {
        DataStorage.clearListedAppListing();
        GenericUrl url = new GoogleUrl(EnterpriseMarketplaceUrl.generateAppListingUrl() + DataStorage.getVendorProfile().vendorId);
        AppListing appListingBody = JavaCIPUnknownScope.buildAppListing(JavaCIPUnknownScope.appsMarketplaceProject);
        JsonHttpContent content = new JsonHttpContent();
        content.jsonFactory = JavaCIPUnknownScope.jsonFactory;
        if (appListingBody != null) {
            content.data = appListingBody;
        }
        AppListing appListing;
        try {
            HttpRequest request = JavaCIPUnknownScope.requestFactory.buildPutRequest(url, content);
            request.addParser(JavaCIPUnknownScope.jsonHttpParser);
            request.readTimeout = JavaCIPUnknownScope.readTimeout;
            HttpResponse response = request.execute();
            appListing = response.parseAs(AppListing.class);
            JavaCIPUnknownScope.operationStatus = JavaCIPUnknownScope.validateAppListing(appListing, appListingBody);
            if (JavaCIPUnknownScope.operationStatus) {
                DataStorage.setListedAppListing(appListing);
            }
            response.getContent().close();
        } catch (IORuntimeException e) {
            AppsMarketplacePluginLog.logError(e);
        }
    }
}
