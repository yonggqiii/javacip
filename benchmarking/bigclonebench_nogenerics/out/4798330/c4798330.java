class c4798330 {

    public static String MD5(String source) {
        JavaCIPUnknownScope.logger.info(source);
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            byte[] bytes = digest.digest();
            result = EncodeUtils.hexEncode(bytes);
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        JavaCIPUnknownScope.logger.info(result);
        return result;
    }
}
