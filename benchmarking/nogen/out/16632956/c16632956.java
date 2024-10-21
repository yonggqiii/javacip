class c16632956 {

    public HttpResponse navigateTo(URI url) {
        JavaCIPUnknownScope.log.debug("navigateTo: " + url.toString());
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = JavaCIPUnknownScope.client.execute(get);
            JavaCIPUnknownScope.log.debug(response.getStatusLine());
            return response;
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
