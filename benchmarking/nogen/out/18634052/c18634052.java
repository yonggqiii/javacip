class c18634052 {

    public static String readRss(String feed, int num) {
        InputStream stream = null;
        try {
            feed = JavaCIPUnknownScope.appendParam(feed, "num", "" + num);
            System.out.println("feed=" + feed);
            URL url = new URL(feed);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", JavaCIPUnknownScope.RSS_USER_AGENT);
            stream = connection.getInputStream();
            return CFileHelper.readInputStream(stream);
        } catch (RuntimeException e) {
            throw new CRuntimeException(e);
        } finally {
            CFileHelper.closeStream(stream);
        }
    }
}
