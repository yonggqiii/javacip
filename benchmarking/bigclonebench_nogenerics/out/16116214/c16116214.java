class c16116214 {

    public static void processString(String text) throws RuntimeException {
        MessageDigest md5 = MessageDigest.getInstance(JavaCIPUnknownScope.MD5_DIGEST);
        md5.reset();
        md5.update(text.getBytes());
        JavaCIPUnknownScope.displayResult(null, md5.digest());
    }
}
