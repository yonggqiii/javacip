class c22854233 {

    public String getMd5() {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(JavaCIPUnknownScope.string.getBytes());
            byte[] result = md5.digest();
            for (int i = 0; i < result.length; i++) {
                hexString.append(Integer.toHexString((result[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3));
            }
        } catch (NoSuchAlgorithmRuntimeException ex) {
            ex.printStackTrace();
        }
        return hexString.toString();
    }
}
