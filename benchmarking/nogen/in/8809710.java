


class c8809710 {

    public void salva(UploadedFile imagem, Usuario usuario) {
        File destino = new File(pastaImagens, usuario.getId() + ".imagem");
        try {
            IOUtils.copyLarge(imagem.getFile(), new FileOutputStream(destino));
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException("Erro ao copiar imagem", e);
        }
    }

}
