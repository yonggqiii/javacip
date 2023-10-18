class c6032985 {

    private byte[] md5Digest(String pPassword) {
        if (pPassword == null) {
            throw new NullPointerRuntimeException("input null text for hashing");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pPassword.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException("Cannot find MD5 algorithm");
        }
    }
}
