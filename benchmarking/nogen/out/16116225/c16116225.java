class c16116225 {

    public void process(String t) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(JavaCIPUnknownScope.MD5_DIGEST);
            md5.reset();
            md5.update(t.getBytes());
            JavaCIPUnknownScope.callback.display(null, JavaCIPUnknownScope.digestToHexString(md5.digest()));
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.callback.display(null, "[failed]");
        }
    }
}
