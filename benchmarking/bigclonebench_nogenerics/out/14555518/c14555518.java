class c14555518 {

    public boolean decrypt(int idAnexo) {
        try {
            Anexo anexo = JavaCIPUnknownScope.anexoService.selectById(idAnexo);
            JavaCIPUnknownScope.aes.init(Cipher.DECRYPT_MODE, JavaCIPUnknownScope.aeskeySpec);
            CipherInputStream cis = new CipherInputStream(new FileInputStream(JavaCIPUnknownScope.config.baseDir + "/arquivos_upload_direto/encrypt/" + anexo.getAnexoCaminho()), JavaCIPUnknownScope.aes);
            FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.config.baseDir + "/arquivos_upload_direto/decrypt/" + anexo.getAnexoCaminho());
            IOUtils.copy(cis, fos);
            cis.close();
            fos.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
