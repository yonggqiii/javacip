class c17391463 {

    public String hash(String plaintext, String salt, int iterations) throws EncryptionRuntimeException {
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(JavaCIPUnknownScope.hashAlgorithm);
            digest.reset();
            digest.update(JavaCIPUnknownScope.ESAPI.securityConfiguration().getMasterSalt());
            digest.update(salt.getBytes(JavaCIPUnknownScope.encoding));
            digest.update(plaintext.getBytes(JavaCIPUnknownScope.encoding));
            bytes = digest.digest();
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                bytes = digest.digest(bytes);
            }
            String encoded = JavaCIPUnknownScope.ESAPI.encoder().encodeForBase64(bytes, false);
            return encoded;
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new EncryptionRuntimeException("Internal error", "Can't find hash algorithm " + JavaCIPUnknownScope.hashAlgorithm, e);
        } catch (UnsupportedEncodingRuntimeException ex) {
            throw new EncryptionRuntimeException("Internal error", "Can't find encoding for " + JavaCIPUnknownScope.encoding, ex);
        }
    }
}
