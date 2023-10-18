class c18599251 {

    public static String hash(String plainTextPwd) {
        MessageDigest hashAlgo;
        try {
            hashAlgo = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new QwickRuntimeException(e);
        }
        hashAlgo.update(plainTextPwd.getBytes());
        return new String(hashAlgo.digest());
    }
}
