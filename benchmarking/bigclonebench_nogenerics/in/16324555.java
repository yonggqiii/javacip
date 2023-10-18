


class c16324555 {

    public static String gerarDigest(String mensagem) {
        String mensagemCriptografada = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            System.out.println("Mensagem original: " + mensagem);
            md.update(mensagem.getBytes());
            byte[] digest = md.digest();
            mensagemCriptografada = converterBytesEmHexa(digest);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return mensagemCriptografada;
    }

}
