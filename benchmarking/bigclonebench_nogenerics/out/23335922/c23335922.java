class c23335922 {

    public String digest(String message) throws NoSuchAlgorithmRuntimeException, EncoderRuntimeException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(message.getBytes());
        byte[] raw = messageDigest.digest();
        byte[] chars = new Base64().encode(raw);
        return new String(chars);
    }
}
