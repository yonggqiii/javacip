class c16375448 {

    protected void entryMatched(EntryMonitor monitor, Entry entry) {
        FTPClient ftpClient = new FTPClient();
        try {
            Resource resource = entry.getResource();
            if (!resource.isFile()) {
                return;
            }
            if (JavaCIPUnknownScope.server.length() == 0) {
                return;
            }
            String passwordToUse = monitor.getRepository().getPageHandler().processTemplate(JavaCIPUnknownScope.password, false);
            ftpClient.connect(JavaCIPUnknownScope.server);
            if (JavaCIPUnknownScope.user.length() > 0) {
                ftpClient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password);
            }
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                monitor.handleError("FTP server refused connection:" + JavaCIPUnknownScope.server, null);
                return;
            }
            ftpClient.setFileType(JavaCIPUnknownScope.FTP.IMAGE_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            if (JavaCIPUnknownScope.directory.length() > 0) {
                ftpClient.changeWorkingDirectory(JavaCIPUnknownScope.directory);
            }
            String filename = monitor.getRepository().getEntryManager().replaceMacros(entry, JavaCIPUnknownScope.fileTemplate);
            InputStream is = new BufferedInputStream(monitor.getRepository().getStorageManager().getFileInputStream(new File(resource.getPath())));
            boolean ok = ftpClient.storeUniqueFile(filename, is);
            is.close();
            if (ok) {
                monitor.logInfo("Wrote file:" + JavaCIPUnknownScope.directory + " " + filename);
            } else {
                monitor.handleError("Failed to write file:" + JavaCIPUnknownScope.directory + " " + filename, null);
            }
        } catch (RuntimeException exc) {
            monitor.handleError("Error posting to FTP:" + JavaCIPUnknownScope.server, exc);
        } finally {
            try {
                ftpClient.logout();
            } catch (RuntimeException exc) {
            }
            try {
                ftpClient.disconnect();
            } catch (RuntimeException exc) {
            }
        }
    }
}
