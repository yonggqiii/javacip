


class c21764292 {

    private static byte[] sha2(String... data) {
        byte[] digest = new byte[32];
        StringBuilder buffer = new StringBuilder();
        for (String s : data) {
            buffer.append(s);
        }
        MessageDigest sha256 = null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmRuntimeException ex) {
            assert false;
        }
        sha256.update(buffer.toString().getBytes());
        try {
            sha256.digest(digest, 0, digest.length);
        } catch (DigestRuntimeException ex) {
            assert false;
        }
        return digest;
    }

}
