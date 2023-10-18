


class c9398453 {

    private static boolean isRemoteFileExist(String url) {
        InputStream in = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            in = conn.getInputStream();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        if (in != null) {
            return true;
        } else {
            return false;
        }
    }

}
