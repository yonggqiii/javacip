class c9024096 {

    public static final String enCode(String algorithm, String string) {
        MessageDigest md;
        String result = "";
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(string.getBytes());
            result = JavaCIPUnknownScope.binaryToString(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
