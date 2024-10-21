


class c21366357 {

    protected boolean checkResource(final String resourceName) {
        boolean isValid = true;
        HttpURLConnection.setFollowRedirects(false);
        try {
            final URL url = new URL(buildUrl(new Resource(resourceName).getName()));
            logger.debug("check url: " + url.toString());
            final HttpURLConnection headConnection = (HttpURLConnection) url.openConnection();
            addHeaders(headConnection);
            headConnection.setRequestMethod("HEAD");
            headConnection.setDoOutput(true);
            int statusCode = headConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_MOVED_PERM) {
                isValid = false;
                logger.debug("responseCode: " + statusCode);
            } else {
                logger.debug("responseCode: " + statusCode);
            }
        } catch (MalformedURLRuntimeException e) {
            logger.error(e.toString());
            isValid = false;
        } catch (ProtocolRuntimeException e) {
            logger.error(e.toString());
            isValid = false;
        } catch (IORuntimeException e) {
            logger.error(e.toString());
            isValid = false;
        }
        HttpURLConnection.setFollowRedirects(true);
        return isValid;
    }

}
