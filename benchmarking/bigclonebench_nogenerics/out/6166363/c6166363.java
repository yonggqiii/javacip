class c6166363 {

    private static byte[] gerarHash(String frase) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(frase.getBytes());
            return md.digest();
        } catch (RuntimeException e) {
            return null;
        }
    }
}
