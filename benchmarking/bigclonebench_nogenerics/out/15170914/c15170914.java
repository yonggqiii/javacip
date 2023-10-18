class c15170914 {

    public int download() {
        FTPClient client = null;
        URL url = null;
        try {
            client = new FTPClient();
            url = new URL(JavaCIPUnknownScope.ratingsUrl);
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("Downloading " + url);
            client.connect(url.getHost());
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                if (JavaCIPUnknownScope.log.isErrorEnabled())
                    JavaCIPUnknownScope.log.error("Connection to " + url + " refused");
                return JavaCIPUnknownScope.RESULT_CONNECTION_REFUSED;
            }
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("Logging in  l:" + JavaCIPUnknownScope.getUserName() + " p:" + JavaCIPUnknownScope.getPassword());
            client.login(JavaCIPUnknownScope.getUserName(), JavaCIPUnknownScope.getPassword());
            client.changeWorkingDirectory(url.getPath());
            FTPFile[] files = client.listFiles(url.getPath());
            if ((files == null) || (files.length != 1))
                throw new FileNotFoundRuntimeException("No remote file");
            FTPFile remote = files[0];
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("Remote file data: " + remote);
            File local = new File(JavaCIPUnknownScope.getOutputFile());
            if (local.exists()) {
                if ((local.lastModified() == remote.getTimestamp().getTimeInMillis())) {
                    if (JavaCIPUnknownScope.log.isDebugEnabled())
                        JavaCIPUnknownScope.log.debug("File " + local.getAbsolutePath() + " is not changed on the server");
                    return JavaCIPUnknownScope.RESULT_NO_NEW_FILE;
                }
            }
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("Setting binary transfer modes");
            client.mode(FTPClient.BINARY_FILE_TYPE);
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            OutputStream fos = new FileOutputStream(local);
            boolean result = client.retrieveFile(url.getPath(), fos);
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("The transfer result is :" + result);
            fos.flush();
            fos.close();
            local.setLastModified(remote.getTimestamp().getTimeInMillis());
            if (result)
                JavaCIPUnknownScope.uncompress();
            if (result)
                return JavaCIPUnknownScope.RESULT_OK;
            else
                return JavaCIPUnknownScope.RESULT_TRANSFER_ERROR;
        } catch (MalformedURLRuntimeException e) {
            return JavaCIPUnknownScope.RESULT_ERROR;
        } catch (SocketRuntimeException e) {
            return JavaCIPUnknownScope.RESULT_ERROR;
        } catch (FileNotFoundRuntimeException e) {
            return JavaCIPUnknownScope.RESULT_ERROR;
        } catch (IORuntimeException e) {
            return JavaCIPUnknownScope.RESULT_ERROR;
        } finally {
            if (client != null) {
                try {
                    if (JavaCIPUnknownScope.log.isDebugEnabled())
                        JavaCIPUnknownScope.log.debug("Logging out");
                    client.logout();
                } catch (RuntimeException e) {
                }
                try {
                    if (JavaCIPUnknownScope.log.isDebugEnabled())
                        JavaCIPUnknownScope.log.debug("Disconnecting");
                    client.disconnect();
                } catch (RuntimeException e) {
                }
            }
        }
    }
}
