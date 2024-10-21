class c10361370 {

    public static byte[] hash(final byte[] saltBefore, final String content, final byte[] saltAfter, final int repeatedHashingCount) throws NoSuchAlgorithmRuntimeException, UnsupportedEncodingRuntimeException {
        if (content == null)
            return null;
        final MessageDigest digest = MessageDigest.getInstance(JavaCIPUnknownScope.DIGEST);
        if (JavaCIPUnknownScope.digestLength == -1)
            JavaCIPUnknownScope.digestLength = digest.getDigestLength();
        for (int i = 0; i < repeatedHashingCount; i++) {
            if (i > 0)
                digest.update(digest.digest());
            digest.update(saltBefore);
            digest.update(content.getBytes(WebCastellumFilter.DEFAULT_CHARACTER_ENCODING));
            digest.update(saltAfter);
        }
        return digest.digest();
    }
}
