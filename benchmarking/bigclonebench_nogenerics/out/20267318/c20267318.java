class c20267318 {

    public String getHash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] toChapter1Digest = md.digest();
            return Keystore.hexEncode(toChapter1Digest);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error("Error in creating DN hash: " + e.getMessage());
            return null;
        }
    }
}
