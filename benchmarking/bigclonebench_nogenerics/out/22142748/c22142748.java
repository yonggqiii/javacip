class c22142748 {

    public static String getMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String out = "";
            for (int i = 0; i < digest.length; i++) {
                out += digest[i];
            }
            return out;
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.err.println("Manca l'MD5 (piuttosto strano)");
        }
        return "";
    }
}
