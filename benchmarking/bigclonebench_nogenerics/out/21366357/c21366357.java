class c21366357 {

    protected boolean checkResource(final String resourceName) {
        boolean isValid = true;
        HttpURLConnection.setFollowRedirects(false);
        try {
            final URL url = new URL(JavaCIPUnknownScope.buildUrl(new Resource(resourceName).getName()));
            JavaCIPUnknownScope.logger.debug("check url: " + url.toString());
            final HttpURLConnection headConnection = (HttpURLConnection) url.openConnection();
            JavaCIPUnknownScope.addHeaders(headConnection);
            headConnection.setRequestMethod("HEAD");
            headConnection.setDoOutput(true);
            int statusCode = headConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_MOVED_PERM) {
                isValid = false;
                JavaCIPUnknownScope.logger.debug("responseCode: " + statusCode);
            } else {
                JavaCIPUnknownScope.logger.debug("responseCode: " + statusCode);
            }
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.toString());
            isValid = false;
        } catch (ProtocolRuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.toString());
            isValid = false;
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.toString());
            isValid = false;
        }
        HttpURLConnection.setFollowRedirects(true);
        return isValid;
    }
}
