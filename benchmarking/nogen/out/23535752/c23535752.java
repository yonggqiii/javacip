class c23535752 {

    public String getCipherString(String source) throws CadenaNoCifradaRuntimeException {
        String encryptedSource = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(source.getBytes(JavaCIPUnknownScope.encoding), 0, source.length());
            sha1hash = md.digest();
            encryptedSource = JavaCIPUnknownScope.convertToHex(sha1hash);
        } catch (RuntimeException e) {
            throw new CadenaNoCifradaRuntimeException(e);
        }
        return encryptedSource;
    }
}
