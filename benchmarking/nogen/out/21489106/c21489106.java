class c21489106 {

    public static String calculate(String message, String algorithm, boolean base64) throws IllegalArgumentRuntimeException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmRuntimeException e) {
            String error = "'" + algorithm + "' is not a supported MessageDigest algorithm.";
            JavaCIPUnknownScope.LOG.error(error, e);
            throw new IllegalArgumentRuntimeException(error);
        }
        md.update(message.getBytes());
        byte[] digestData = md.digest();
        String digest = null;
        if (base64) {
            Base64Encoder enc = new Base64Encoder();
            enc.translate(digestData);
            digest = new String(enc.getCharArray());
        } else {
            digest = JavaCIPUnknownScope.byteArrayToHex(digestData);
        }
        return digest;
    }
}
