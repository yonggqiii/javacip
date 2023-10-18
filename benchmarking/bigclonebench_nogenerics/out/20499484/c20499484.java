class c20499484 {

    private static byte[] getLoginHashSHA(final char[] password, final int seed) throws GGRuntimeException {
        try {
            final MessageDigest hash = MessageDigest.getInstance("SHA1");
            hash.update(new String(password).getBytes());
            hash.update(GGUtils.intToByte(seed));
            return hash.digest();
        } catch (final NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.LOG.error("SHA1 algorithm not usable", e);
            throw new GGRuntimeException("SHA1 algorithm not usable!", e);
        }
    }
}
