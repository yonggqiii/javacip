class c4629990 {

    private String logonToServer(FTPClient ftpClient, String ftpAddress, int noRetries) {
        String remoteHomeDir = null;
        JavaCIPUnknownScope.noRetriesSoFar = 0;
        while (true) {
            try {
                ftpClient.connect(ftpAddress, JavaCIPUnknownScope.ftpPort);
                int reply = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    throw new IORuntimeException();
                }
                if (!ftpClient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password)) {
                    throw new IORuntimeException();
                }
                remoteHomeDir = ftpClient.printWorkingDirectory();
                JavaCIPUnknownScope.msgEntry.setAppContext("logonToServer()");
                JavaCIPUnknownScope.msgEntry.setMessageText("Logged into FTP server " + ftpAddress + ":" + JavaCIPUnknownScope.ftpPort + " as user " + JavaCIPUnknownScope.user);
                JavaCIPUnknownScope.logger.logProcess(JavaCIPUnknownScope.msgEntry);
                break;
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.logoutAndDisconnect(ftpClient);
                if (JavaCIPUnknownScope.noRetriesSoFar++ < noRetries) {
                    JavaCIPUnknownScope.waitBetweenRetry();
                    JavaCIPUnknownScope.notifyAndStartWaitingFlag = false;
                } else {
                    JavaCIPUnknownScope.notifyAndStartWaitingFlag = true;
                    JavaCIPUnknownScope.errEntry.setThrowable(e);
                    JavaCIPUnknownScope.errEntry.setAppContext("logonToServer()");
                    JavaCIPUnknownScope.errEntry.setAppMessage("Unable to login after " + (JavaCIPUnknownScope.noRetriesSoFar - 1) + " retries. Max Retries.\n" + "Address:" + ftpAddress + "\n" + "User:" + JavaCIPUnknownScope.user);
                    JavaCIPUnknownScope.errEntry.setSubjectSendEmail("Unable to login to " + ftpAddress + " after " + (JavaCIPUnknownScope.noRetriesSoFar - 1) + " retries.");
                    JavaCIPUnknownScope.logger.logError(JavaCIPUnknownScope.errEntry);
                    break;
                }
            }
        }
        return remoteHomeDir;
    }
}
