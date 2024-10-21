


class c8320469 {

    public static String md5(String text) {
        String encrypted = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            encrypted = hex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException nsaEx) {
        }
        return encrypted;
    }

}
