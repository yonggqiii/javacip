class c9130471 {

    void retrieveSupplementalInfo() throws IORuntimeException, InterruptedRuntimeException {
        String encodedProductID = URLEncoder.encode(JavaCIPUnknownScope.productID, "UTF-8");
        String uri = JavaCIPUnknownScope.BASE_PRODUCT_URI + encodedProductID;
        HttpUriRequest head = new HttpGet(uri);
        AndroidHttpClient client = AndroidHttpClient.newInstance(null);
        HttpResponse response = client.execute(head);
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            return;
        }
        String content = JavaCIPUnknownScope.consume(response.getEntity());
        Matcher matcher = JavaCIPUnknownScope.PRODUCT_NAME_PRICE_PATTERN.matcher(content);
        if (matcher.find()) {
            JavaCIPUnknownScope.append(matcher.group(1));
            JavaCIPUnknownScope.append(matcher.group(2));
        }
        JavaCIPUnknownScope.setLink(uri);
    }
}
