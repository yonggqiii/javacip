


class c14945924 {

    public static String hashPassword(String password) {
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_MD5);
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmRuntimeException e) {
            logger.error("Cannot find algorithm = '" + MESSAGE_DIGEST_ALGORITHM_MD5 + "'", e);
            throw new IllegalStateRuntimeException(e);
        }
        return pad(hashword, 32, '0');
    }

}
