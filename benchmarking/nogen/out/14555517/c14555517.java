class c14555517 {

    public boolean encrypt(int idAnexo) {
        try {
            Anexo anexo = JavaCIPUnknownScope.anexoService.selectById(idAnexo);
            JavaCIPUnknownScope.aes.init(Cipher.ENCRYPT_MODE, JavaCIPUnknownScope.aeskeySpec);
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.config.baseDir + "/arquivos_upload_direto/" + anexo.getAnexoCaminho());
            CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(JavaCIPUnknownScope.config.baseDir + "/arquivos_upload_direto/encrypt/" + anexo.getAnexoCaminho()), JavaCIPUnknownScope.aes);
            IOUtils.copy(fis, cos);
            cos.close();
            fis.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
