


class c16632956 {

    public HttpResponse navigateTo(URI url) {
        log.debug("navigateTo: " + url.toString());
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            log.debug(response.getStatusLine());
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
