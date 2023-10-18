


class c5565900 {

    public Resource get(String serviceUrl, String resourceId, String svcId, boolean appendProfile) throws RuntimeException {
        Resource resource = new Resource();
        String openurl = getURL(serviceUrl, resourceId, svcId, appendProfile);
        logger.debug("OpenURL Request: " + openurl);
        URL url;
        try {
            url = new URL(openurl);
            HttpURLConnection huc = (HttpURLConnection) (url.openConnection());
            int code = huc.getResponseCode();
            if (code == 200) {
                InputStream is = huc.getInputStream();
                resource.setBytes(IOUtils.getByteArray(is));
                resource.setContentType(huc.getContentType());
            } else if (code == 404) {
                return null;
            } else {
                logger.error("An error of type " + code + " occurred for " + url.toString());
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
