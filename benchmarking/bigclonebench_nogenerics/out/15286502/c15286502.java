class c15286502 {

    public static String md5(String senha) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.out.println("Ocorreu NoSuchAlgorithmRuntimeException");
        }
        md.update(senha.getBytes());
        byte[] xx = md.digest();
        String n2 = null;
        StringBuffer resposta = new StringBuffer();
        for (int i = 0; i < xx.length; i++) {
            n2 = Integer.toHexString(0XFF & ((int) (xx[i])));
            if (n2.length() < 2) {
                n2 = "0" + n2;
            }
            resposta.append(n2);
        }
        return resposta.toString();
    }
}
