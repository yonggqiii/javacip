class c4798332 {

    public static String SHA(String source) {
        JavaCIPUnknownScope.logger.info(source);
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
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
