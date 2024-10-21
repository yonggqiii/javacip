class c4798331 {

    public static String SHA256(String source) {
        JavaCIPUnknownScope.logger.info(source);
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
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
