class c8416636 {

    private static byte[] Md5f(String plainText) {
        byte[] ab = new byte[16];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            ab = b;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return ab;
    }
}
