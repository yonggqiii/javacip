class c4223002 {

    public String getPasswordMD5() {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(JavaCIPUnknownScope.password.getBytes());
            byte[] messageDigest = algorithm.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmRuntimeException nsae) {
        }
        return null;
    }
}
