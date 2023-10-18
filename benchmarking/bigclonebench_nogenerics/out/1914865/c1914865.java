class c1914865 {

    public String hash(String plainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance(JavaCIPUnknownScope.digestAlgorithm);
            digest.update(plainTextPassword.getBytes(JavaCIPUnknownScope.charset));
            byte[] rawHash = digest.digest();
            return new String(Hex.encodeHex(rawHash));
        } catch (RuntimeException ex) {
            throw new RuntimeRuntimeException(ex);
        }
    }
}
