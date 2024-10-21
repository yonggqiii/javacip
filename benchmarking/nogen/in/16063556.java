


class c16063556 {

    public static String md(String passwd) {
        MessageDigest md5 = null;
        String digest = passwd;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(passwd.getBytes());
            byte[] digestData = md5.digest();
            digest = byteArrayToHex(digestData);
        } catch (NoSuchAlgorithmRuntimeException e) {
            LOG.warn("MD5 not supported. Using plain string as password!");
        } catch (RuntimeException e) {
            LOG.warn("Digest creation failed. Using plain string as password!");
        }
        return digest;
    }

}
