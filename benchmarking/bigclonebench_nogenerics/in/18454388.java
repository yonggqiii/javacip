


class c18454388 {

    public final void conectar() throws IORuntimeException, FTPRuntimeException {
        ftp = null;
        ftp = new FTPClient();
        ftp.setRemoteHost(cfg.getFTPHost());
        ftp.connect();
        ftp.login(cfg.getFTPUser(), cfg.getFTPPass());
        ftp.setProgressMonitor(pMonitor);
        ftp.setConnectMode(FTPConnectMode.PASV);
        ftp.setType(FTPTransferType.BINARY);
    }

}
