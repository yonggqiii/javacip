class c11090448 {

    public static Object fetchCached(String address, int hours) throws MalformedURLRuntimeException, IORuntimeException {
        String cacheName = JavaCIPUnknownScope.md5(address);
        JavaCIPUnknownScope.checkAndCreateDirectoryIfNeeded();
        File r = new File(JavaCIPUnknownScope.CACHELOCATION + cacheName);
        Date d = new Date();
        long limit = d.getTime() - (1000 * 60 * 60 * hours);
        if (!r.exists() || (hours != -1 && r.lastModified() < limit)) {
            InputStream is = (InputStream) JavaCIPUnknownScope.fetch(address);
            FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.CACHELOCATION + cacheName);
            int nextChar;
            while ((nextChar = is.read()) != -1) fos.write((char) nextChar);
            fos.flush();
        }
        FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.CACHELOCATION + cacheName);
        return fis;
    }
}
