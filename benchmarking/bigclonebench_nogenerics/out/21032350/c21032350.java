class c21032350 {

    public byte[] md5(String clearText) {
        MessageDigest md;
        byte[] digest;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(clearText.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new UnsupportedOperationRuntimeException(e.toString());
        }
        return digest;
    }
}
