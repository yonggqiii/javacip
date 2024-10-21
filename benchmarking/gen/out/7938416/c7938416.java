class c7938416 {

    public synchronized boolean copy(int idAnexo) {
        try {
            Anexo anexo = JavaCIPUnknownScope.selectById(idAnexo);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) auth.getPrincipal();
            if (anexo.getAssinado() == 1 && anexo.getIdAssinadoPor() != usuario.getIdUsuario()) {
                JavaCIPUnknownScope.deleteAnexoFromTemp(anexo);
                return false;
            }
            Carteira carteiraUsuario = JavaCIPUnknownScope.carteiraService.selectById(usuario.getIdCarteira());
            DocumentoDetalhes documentoDetalhes = anexo.getDocumentoDetalhes();
            Set<Documento> documentos = documentoDetalhes.getDocumentosByCarteira();
            boolean havePermission = false;
            for (Documento documento : documentos) {
                Carteira carteiraDocumento = documento.getCarteira();
                if (carteiraDocumento != null) {
                    if (carteiraDocumento.getIdCarteira() == carteiraUsuario.getIdCarteira()) {
                        havePermission = true;
                        System.out.println("tem permisssao: " + havePermission);
                        break;
                    }
                }
            }
            if (!havePermission) {
                System.out.println("Não tem permissao.");
                return false;
            }
            FileInputStream fis = new FileInputStream(new File(JavaCIPUnknownScope.config.baseDir + "/temp/" + anexo.getAnexoCaminho()));
            FileOutputStream fos = new FileOutputStream(new File(JavaCIPUnknownScope.config.baseDir + "/arquivos_upload_direto/" + anexo.getAnexoCaminho()));
            IOUtils.copy(fis, fos);
            String txtHistorico = "(Edição) -" + anexo.getAnexoNome() + "-";
            txtHistorico += usuario.getUsuLogin();
            Historico historico = new Historico();
            historico.setCarteira(carteiraUsuario);
            historico.setDataHoraHistorico(new Date());
            historico.setHistorico(txtHistorico);
            historico.setDocumentoDetalhes(documentoDetalhes);
            historico.setUsuario(usuario);
            JavaCIPUnknownScope.historicoService.save(historico);
            return JavaCIPUnknownScope.deleteAnexoFromTemp(anexo);
        } catch (FileNotFoundRuntimeException e) {
            System.out.println("FileNotFoundRuntimeException");
            e.printStackTrace();
            return false;
        } catch (IORuntimeException e) {
            System.out.println("IORuntimeException");
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            System.out.println("AnexoServiceImpl.copy ERRO DESCONHECIDO");
            e.printStackTrace();
            return false;
        }
    }
}
