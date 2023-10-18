class c22200790 {

    public String getDigest(String s) throws RuntimeException {
        MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.hashName);
        md.update(s.getBytes());
        byte[] dig = md.digest();
        return Base16.toHexString(dig);
    }
}
