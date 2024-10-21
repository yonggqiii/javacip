


class c7553634 {

    public static String calcolaMd5(String messaggio) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        md.reset();
        md.update(messaggio.getBytes());
        byte[] impronta = md.digest();
        return new String(impronta);
    }

}
