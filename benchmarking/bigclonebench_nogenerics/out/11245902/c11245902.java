class c11245902 {

    public static void uploadFile(File in, String out, String host, int port, String path, String login, String password, boolean renameIfExist) throws IORuntimeException {
        FTPClient ftp = null;
        try {
            JavaCIPUnknownScope.m_logCat.info("Uploading " + in + " to " + host + ":" + port + " at " + path);
            ftp = new FTPClient();
            int reply;
            ftp.connect(host, port);
            JavaCIPUnknownScope.m_logCat.info("Connected to " + host + "... Trying to authenticate");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                JavaCIPUnknownScope.m_logCat.error("FTP server " + host + " refused connection.");
                throw new IORuntimeException("Cannot connect to the FTP Server: connection refused.");
            }
            if (!ftp.login(login, password)) {
                ftp.logout();
                throw new IORuntimeException("Cannot connect to the FTP Server: login / password is invalid!");
            }
            ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            if (!ftp.changeWorkingDirectory(path)) {
                JavaCIPUnknownScope.m_logCat.warn("Remote working directory: " + path + "does not exist on the FTP Server ...");
                JavaCIPUnknownScope.m_logCat.info("Trying to create remote directory: " + path);
                if (!ftp.makeDirectory(path)) {
                    JavaCIPUnknownScope.m_logCat.error("Failed to create remote directory: " + path);
                    throw new IORuntimeException("Failed to store " + in + " in the remote directory: " + path);
                }
                if (!ftp.changeWorkingDirectory(path)) {
                    JavaCIPUnknownScope.m_logCat.error("Failed to change directory. Unexpected error");
                    throw new IORuntimeException("Failed to change to remote directory : " + path);
                }
            }
            if (out == null) {
                out = in.getName();
                if (out.startsWith("/")) {
                    out = out.substring(1);
                }
            }
            if (renameIfExist) {
                String[] files = ftp.listNames();
                String f = in + out;
                for (int i = 0; i < files.length; i++) {
                    if (files[i].equals(out)) {
                        JavaCIPUnknownScope.m_logCat.debug("Found existing file on the server: " + out);
                        boolean rename_ok = false;
                        String bak = "_bak";
                        int j = 0;
                        String newExt = null;
                        while (!rename_ok) {
                            if (j == 0)
                                newExt = bak;
                            else
                                newExt = bak + j;
                            if (ftp.rename(out, out + newExt)) {
                                JavaCIPUnknownScope.m_logCat.info(out + " renamed to " + out + newExt);
                                rename_ok = true;
                            } else {
                                JavaCIPUnknownScope.m_logCat.warn("Renaming to " + out + newExt + " has failed!, trying again ...");
                                j++;
                            }
                        }
                        break;
                    }
                }
            }
            InputStream input = new FileInputStream(in);
            JavaCIPUnknownScope.m_logCat.info("Starting transfert of " + in);
            ftp.storeFile(out, input);
            JavaCIPUnknownScope.m_logCat.info(in + " uploaded successfully");
            input.close();
            ftp.logout();
        } catch (FTPConnectionClosedRuntimeException e) {
            JavaCIPUnknownScope.m_logCat.error("Server closed connection.", e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException f) {
                }
            }
        }
    }
}
