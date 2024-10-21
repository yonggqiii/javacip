


class c5048220 {

    private int connect() {
        if (ftp.isConnected()) {
            log.debug("Already connected to: " + getConnectionString());
            return RET_OK;
        }
        try {
            ftp.connect(server, port);
            ftp.login(username, password);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (SocketRuntimeException e) {
            log.error(e.toString());
            return RET_ERR_SOCKET;
        } catch (UnknownHostRuntimeException e) {
            log.error(e.toString());
            return RET_ERR_UNKNOWN_HOST;
        } catch (FTPConnectionClosedRuntimeException e) {
            log.error(e.toString());
            return RET_ERR_FTP_CONN_CLOSED;
        } catch (IORuntimeException e) {
            log.error(e.toString());
            return RET_ERR_IO;
        }
        if (ftp.isConnected()) {
            log.debug("Connected to " + getConnectionString());
            return RET_OK;
        }
        log.debug("Could not connect to " + getConnectionString());
        return RET_ERR_NOT_CONNECTED;
    }

}
