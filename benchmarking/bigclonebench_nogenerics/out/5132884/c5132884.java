class c5132884 {

    public byte[] encryptMsg(String encryptString) {
        byte[] encryptByte = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(encryptString.getBytes());
            encryptByte = messageDigest.digest();
        } catch (NoSuchAlgorithmRuntimeException ex) {
            ex.printStackTrace();
        }
        return encryptByte;
    }
}
