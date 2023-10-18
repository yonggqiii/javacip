class c11513043 {

    public String hash(String password) {
        MessageDigest sha1Digest;
        try {
            sha1Digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw NestedRuntimeException.wrap(e);
        }
        sha1Digest.update(password.getBytes());
        StringBuilder retval = new StringBuilder("sha1:");
        retval.append(new String(Base64.encodeBase64(sha1Digest.digest())));
        return retval.toString();
    }
}
