class c18454388 {

    public final void conectar() throws IORuntimeException, FTPRuntimeException {
        JavaCIPUnknownScope.ftp = null;
        JavaCIPUnknownScope.ftp = new FTPClient();
        JavaCIPUnknownScope.ftp.setRemoteHost(JavaCIPUnknownScope.cfg.getFTPHost());
        JavaCIPUnknownScope.ftp.connect();
        JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.cfg.getFTPUser(), JavaCIPUnknownScope.cfg.getFTPPass());
        JavaCIPUnknownScope.ftp.setProgressMonitor(JavaCIPUnknownScope.pMonitor);
        JavaCIPUnknownScope.ftp.setConnectMode(FTPConnectMode.PASV);
        JavaCIPUnknownScope.ftp.setType(FTPTransferType.BINARY);
    }
}
