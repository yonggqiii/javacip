


class c20383360 {

    public static byte[] decrypt(byte[] ciphertext, byte[] key) throws IORuntimeException {
        CryptInputStream in = new CryptInputStream(new ByteArrayInputStream(ciphertext), new SerpentEngine(), key);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        IOUtils.copy(in, bout);
        return bout.toByteArray();
    }

}
