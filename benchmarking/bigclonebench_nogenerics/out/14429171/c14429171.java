class c14429171 {

    public void salva(UploadedFile imagem, Usuario usuario) {
        File destino;
        if (usuario.getId() == null) {
            destino = new File(JavaCIPUnknownScope.pastaImagens, usuario.hashCode() + ".jpg");
        } else {
            destino = new File(JavaCIPUnknownScope.pastaImagens, usuario.getId() + ".jpg");
        }
        try {
            IOUtils.copyLarge(imagem.getFile(), new FileOutputStream(destino));
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException("Erro ao copiar imagem", e);
        }
        JavaCIPUnknownScope.redimensionar(destino.getPath(), destino.getPath(), "jpg", 110, 110);
    }
}
