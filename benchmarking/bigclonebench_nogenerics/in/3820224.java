


class c3820224 {

    private String hashString(String key) {
        MessageDigest digest;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            byte[] hash = digest.digest();
            BigInteger bi = new BigInteger(1, hash);
            return String.format("%0" + (hash.length << 1) + "X", bi) + KERNEL_VERSION;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return "" + key.hashCode();
        }
    }

}
