class c5645175 {

    private String generaHashMD5(String plainText) throws RuntimeException {
        MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
        mdAlgorithm.update(plainText.getBytes(FirmaUtil.CHARSET));
        byte[] digest = mdAlgorithm.digest();
        return JavaCIPUnknownScope.toHex(digest);
    }
}
