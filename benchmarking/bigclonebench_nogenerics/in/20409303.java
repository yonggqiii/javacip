


class c20409303 {

    private String md5(String input) {
        MessageDigest md5Digest;
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new UserRuntimeException("could not get a md5 message digest", e);
        }
        md5Digest.update(input.getBytes());
        return new String(md5Digest.digest());
    }

}
