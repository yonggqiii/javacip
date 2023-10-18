class c15826301 {

    public static int getContentLength(String address) {
        URLConnection conn = null;
        int contentLength = 0;
        try {
            URL url = new URL(address);
            conn = url.openConnection();
            contentLength = conn.getContentLength();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return contentLength;
    }
}
