class c9918989 {

    private byte[] hash(String data, HashAlg alg) {
        try {
            MessageDigest digest = MessageDigest.getInstance(alg.toString());
            digest.update(data.getBytes());
            byte[] hash = digest.digest();
            return hash;
        } catch (NoSuchAlgorithmRuntimeException e) {
        }
        return null;
    }
}
