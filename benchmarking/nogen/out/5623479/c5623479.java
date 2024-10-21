class c5623479 {

    public static String getHash(String text) {
        String ret = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            ret = JavaCIPUnknownScope.getHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.log.error(e);
            throw new OopsRuntimeException(e, "Hash Error.");
        }
        return ret;
    }
}
