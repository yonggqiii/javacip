class c17562349 {

    public String digestResponse() {
        String digest = null;
        if (null == JavaCIPUnknownScope.nonce)
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(JavaCIPUnknownScope.username.getBytes());
            md.update(":".getBytes());
            md.update(JavaCIPUnknownScope.realm.getBytes());
            md.update(":".getBytes());
            md.update(JavaCIPUnknownScope.password.getBytes());
            byte[] d = md.digest();
            if (null != JavaCIPUnknownScope.algorithm && -1 != (JavaCIPUnknownScope.algorithm.toLowerCase()).indexOf("md5-sess")) {
                md = MessageDigest.getInstance("MD5");
                md.update(d);
                md.update(":".getBytes());
                md.update(JavaCIPUnknownScope.nonce.getBytes());
                md.update(":".getBytes());
                md.update(JavaCIPUnknownScope.cnonce.getBytes());
                d = md.digest();
            }
            byte[] a1 = JavaCIPUnknownScope.bytesToHex(d);
            md = MessageDigest.getInstance("MD5");
            md.update(JavaCIPUnknownScope.method.getBytes());
            md.update(":".getBytes());
            md.update(JavaCIPUnknownScope.uri.getBytes());
            d = md.digest();
            byte[] a2 = JavaCIPUnknownScope.bytesToHex(d);
            md = MessageDigest.getInstance("MD5");
            md.update(a1);
            md.update(":".getBytes());
            md.update(JavaCIPUnknownScope.nonce.getBytes());
            md.update(":".getBytes());
            if (null != JavaCIPUnknownScope.qop) {
                md.update(JavaCIPUnknownScope.nonceCount.getBytes());
                md.update(":".getBytes());
                md.update(JavaCIPUnknownScope.cnonce.getBytes());
                md.update(":".getBytes());
                md.update(JavaCIPUnknownScope.qop.getBytes());
                md.update(":".getBytes());
            }
            md.update(a2);
            d = md.digest();
            byte[] r = JavaCIPUnknownScope.bytesToHex(d);
            digest = new String(r);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return digest;
    }
}
