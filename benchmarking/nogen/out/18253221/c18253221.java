class c18253221 {

    public boolean uploadFTP(String ipFTP, String loginFTP, String senhaFTP, String diretorioFTP, String diretorioAndroid, String arquivoFTP) {
        try {
            JavaCIPUnknownScope.dialogHandler.sendEmptyMessage(0);
            File file = new File(diretorioAndroid);
            File file2 = new File(diretorioAndroid + arquivoFTP);
            Log.v("uploadFTP", "Atribuidas as vari�veis");
            String status = "";
            if (file.isDirectory()) {
                Log.v("uploadFTP", "� diret�rio");
                if (file.list().length > 0) {
                    Log.v("uploadFTP", "file.list().length > 0");
                    JavaCIPUnknownScope.ftp.connect(ipFTP);
                    JavaCIPUnknownScope.ftp.login(loginFTP, senhaFTP);
                    JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
                    JavaCIPUnknownScope.ftp.setFileTransferMode(FTPClient.ASCII_FILE_TYPE);
                    JavaCIPUnknownScope.ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
                    JavaCIPUnknownScope.ftp.changeWorkingDirectory(diretorioFTP);
                    FileInputStream arqEnviar = new FileInputStream(diretorioAndroid + arquivoFTP);
                    Log.v("uploadFTP", "FileInputStream declarado");
                    if (JavaCIPUnknownScope.ftp.storeFile(arquivoFTP, arqEnviar)) {
                        Log.v("uploadFTP", "ftp.storeFile(arquivoFTP, arqEnviar)");
                        status = JavaCIPUnknownScope.ftp.getStatus().toString();
                        Log.v("uploadFTP", "getStatus(): " + status);
                        if (file2.delete()) {
                            Log.i("uploadFTP", "Arquivo " + arquivoFTP + " exclu�do com sucesso");
                            JavaCIPUnknownScope.retorno = true;
                        } else {
                            Log.e("uploadFTP", "Erro ao excluir o arquivo " + arquivoFTP);
                            JavaCIPUnknownScope.retorno = false;
                        }
                    } else {
                        Log.e("uploadFTP", "ERRO: arquivo " + arquivoFTP + "n�o foi enviado!");
                        JavaCIPUnknownScope.retorno = false;
                    }
                } else {
                    Log.e("uploadFTP", "N�o existe o arquivo " + arquivoFTP + "neste diret�rio!");
                    JavaCIPUnknownScope.retorno = false;
                }
            } else {
                Log.e("uploadFTP", "N�o � diret�rio");
                JavaCIPUnknownScope.retorno = false;
            }
            if (JavaCIPUnknownScope.ftp.isConnected()) {
                Log.v("uploadFTP", "isConnected ");
                JavaCIPUnknownScope.ftp.abort();
                status = JavaCIPUnknownScope.ftp.getStatus().toString();
                Log.v("uploadFTP", "quit " + JavaCIPUnknownScope.retorno);
            }
            return JavaCIPUnknownScope.retorno;
        } catch (IORuntimeException e) {
            Log.e("uploadFTP", "ERRO FTP: " + e);
            JavaCIPUnknownScope.retorno = false;
            return JavaCIPUnknownScope.retorno;
        } finally {
            JavaCIPUnknownScope.handler.sendEmptyMessage(0);
            Log.v("uploadFTP", "finally executado");
        }
    }
}
