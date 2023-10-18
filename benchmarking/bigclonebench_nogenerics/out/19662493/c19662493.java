class c19662493 {

    private static String md5Encode(String pass) {
        String string;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] result = md.digest();
            string = JavaCIPUnknownScope.bytes2hexStr(result);
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException("La libreria java.security no implemente MD5");
        }
        return string;
    }
}
