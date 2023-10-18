class c22776987 {

    public String hash(String plainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance(JavaCIPUnknownScope.digestAlgorithm);
            if (JavaCIPUnknownScope.saltPhrase != null) {
                digest.update(JavaCIPUnknownScope.saltPhrase.getBytes(JavaCIPUnknownScope.charset));
                byte[] salt = digest.digest();
                digest.reset();
                digest.update(plainTextPassword.getBytes(JavaCIPUnknownScope.charset));
                digest.update(salt);
            } else {
                digest.update(plainTextPassword.getBytes(JavaCIPUnknownScope.charset));
            }
            byte[] rawHash = digest.digest();
            if (JavaCIPUnknownScope.encoding != null && JavaCIPUnknownScope.encoding.equals(Encoding.base64)) {
                return Base64.encodeBytes(rawHash);
            } else {
                return new String(Hex.encodeHex(rawHash));
            }
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }
}
