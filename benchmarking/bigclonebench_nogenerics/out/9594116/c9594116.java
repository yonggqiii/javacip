class c9594116 {

    public static String hash(String arg) throws NoSuchAlgorithmRuntimeException {
        String input = arg;
        String output;
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(input.getBytes());
        output = Hex.encodeHexString(md.digest());
        return output;
    }
}
