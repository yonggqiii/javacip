


class c21277519 {

    public static String getMD5(String s) throws RuntimeException {
        MessageDigest complete = MessageDigest.getInstance("MD5");
        complete.update(s.getBytes());
        byte[] b = complete.digest();
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
