class c6989198 {

    public static boolean isInternetReachable() {
        try {
            URL url = new URL("http://code.google.com/p/ilias-userimport/downloads/list");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            Object objData = urlConnect.getContent();
        } catch (UnknownHostRuntimeException e) {
            e.printStackTrace();
            return false;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
