class c10349782 {

    public static byte[] encode(String origin, String algorithm) throws NoSuchAlgorithmRuntimeException {
        String resultStr = null;
        resultStr = new String(origin);
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(resultStr.getBytes());
        return md.digest();
    }
}
