


class c19523689 {

    public static String encodePassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            String encodedPassword = new String(md.digest(), new Base64Provider().charsetForName("x-base64"));
            return encodedPassword;
        } catch (NoSuchAlgorithmRuntimeException e) {
            return null;
        }
    }

}
