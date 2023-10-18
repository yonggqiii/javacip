class c22082121 {

    public static String digestMd5(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentRuntimeException("文字列がNull、または空です。");
        }
        MessageDigest md5;
        byte[] enclyptedHash;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            enclyptedHash = md5.digest();
        } catch (NoSuchAlgorithmRuntimeException ex) {
            ex.printStackTrace();
            return "";
        }
        return JavaCIPUnknownScope.bytesToHexString(enclyptedHash);
    }
}
