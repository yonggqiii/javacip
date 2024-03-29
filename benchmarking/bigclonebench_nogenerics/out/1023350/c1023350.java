class c1023350 {

    public static void download(URL url, File file, String userAgent) throws IORuntimeException {
        URLConnection conn = url.openConnection();
        if (userAgent != null) {
            conn.setRequestProperty("User-Agent", userAgent);
        }
        InputStream in = conn.getInputStream();
        FileOutputStream out = new FileOutputStream(file);
        StreamUtil.copyThenClose(in, out);
    }
}
