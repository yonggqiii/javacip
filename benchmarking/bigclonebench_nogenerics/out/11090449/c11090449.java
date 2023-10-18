class c11090449 {

    public static Drawable fetchCachedDrawable(String url) throws MalformedURLRuntimeException, IORuntimeException {
        Log.d(JavaCIPUnknownScope.LOG_TAG, "Fetching cached : " + url);
        String cacheName = JavaCIPUnknownScope.md5(url);
        JavaCIPUnknownScope.checkAndCreateDirectoryIfNeeded();
        File r = new File(JavaCIPUnknownScope.CACHELOCATION + cacheName);
        if (!r.exists()) {
            InputStream is = (InputStream) JavaCIPUnknownScope.fetch(url);
            FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.CACHELOCATION + cacheName);
            int nextChar;
            while ((nextChar = is.read()) != -1) fos.write((char) nextChar);
            fos.flush();
        }
        FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.CACHELOCATION + cacheName);
        Drawable d = Drawable.createFromStream(fis, "src");
        return d;
    }
}
