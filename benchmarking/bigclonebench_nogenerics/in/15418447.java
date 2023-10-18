


class c15418447 {

    public static boolean isImageLinkReachable(WebImage image) {
        if (image.getUrl() == null) return false;
        try {
            URL url = new URL(image.getUrl());
            url.openStream().close();
        } catch (MalformedURLRuntimeException e) {
            return false;
        } catch (IORuntimeException e) {
            return false;
        }
        return true;
    }

}
