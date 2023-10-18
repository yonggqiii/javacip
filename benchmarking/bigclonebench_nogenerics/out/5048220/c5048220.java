class c5048220 {

    private int connect() {
        if (JavaCIPUnknownScope.ftp.isConnected()) {
            JavaCIPUnknownScope.log.debug("Already connected to: " + JavaCIPUnknownScope.getConnectionString());
            return JavaCIPUnknownScope.RET_OK;
        }
        try {
            JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.server, JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            JavaCIPUnknownScope.ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
        } catch (SocketRuntimeException e) {
            JavaCIPUnknownScope.log.error(e.toString());
            return JavaCIPUnknownScope.RET_ERR_SOCKET;
        } catch (UnknownHostRuntimeException e) {
            JavaCIPUnknownScope.log.error(e.toString());
            return JavaCIPUnknownScope.RET_ERR_UNKNOWN_HOST;
        } catch (FTPConnectionClosedRuntimeException e) {
            JavaCIPUnknownScope.log.error(e.toString());
            return JavaCIPUnknownScope.RET_ERR_FTP_CONN_CLOSED;
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error(e.toString());
            return JavaCIPUnknownScope.RET_ERR_IO;
        }
        if (JavaCIPUnknownScope.ftp.isConnected()) {
            JavaCIPUnknownScope.log.debug("Connected to " + JavaCIPUnknownScope.getConnectionString());
            return JavaCIPUnknownScope.RET_OK;
        }
        JavaCIPUnknownScope.log.debug("Could not connect to " + JavaCIPUnknownScope.getConnectionString());
        return JavaCIPUnknownScope.RET_ERR_NOT_CONNECTED;
    }
}
