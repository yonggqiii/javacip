class c22368134 {

    private static void testIfNoneMatch() throws RuntimeException {
        String eTag = JavaCIPUnknownScope.c.getHeaderField("ETag");
        InputStream in = JavaCIPUnknownScope.c.getInputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        do {
            read = in.read(buffer);
            if (read > 0)
                md5.update(buffer, 0, read);
        } while (read < 0);
        byte[] digest = md5.digest();
        String hexDigest = JavaCIPUnknownScope.getHexString(digest);
        if (hexDigest.equals(eTag))
            System.out.print("eTag content          : md5 hex string");
        String quotedHexDigest = "\"" + hexDigest + "\"";
        if (quotedHexDigest.equals(eTag))
            System.out.print("eTag content          : quoted md5 hex string");
        HttpURLConnection c2 = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        c2.addRequestProperty("If-None-Match", eTag);
        c2.connect();
        int code = c2.getResponseCode();
        System.out.print("If-None-Match response: ");
        boolean supported = (code == 304);
        System.out.println(JavaCIPUnknownScope.b2s(supported) + " - " + code + " (" + c2.getResponseMessage() + ")");
    }
}
