class c13979533 {

    public Resource get(URL serviceUrl, String resourceId) throws RuntimeException {
        Resource resource = new Resource();
        String openurl = serviceUrl.toString() + "?url_ver=Z39.88-2004" + "&rft_id=" + URLEncoder.encode(resourceId, "UTF-8") + "&svc_id=" + JavaCIPUnknownScope.SVCID_ADORE4;
        JavaCIPUnknownScope.log.debug("OpenURL Request: " + openurl);
        URL url;
        try {
            url = new URL(openurl);
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                resource.setBytes(StreamUtil.getByteArray(is));
                resource.setContentType(huc.getContentType());
            } else {
                JavaCIPUnknownScope.log.error("An error of type " + code + " occurred for " + url.toString());
                throw new RuntimeException("Cannot get " + url.toString());
            }
        } catch (MalformedURLRuntimeException e) {
            throw new RuntimeException("A MalformedURLRuntimeException occurred for " + openurl);
        } catch (IORuntimeException e) {
            throw new RuntimeException("An IORuntimeException occurred attempting to connect to " + openurl);
        }
        return resource;
    }
}
