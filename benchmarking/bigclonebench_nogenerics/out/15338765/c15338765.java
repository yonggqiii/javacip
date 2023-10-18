class c15338765 {

    public void upload() throws UnknownHostRuntimeException, SocketRuntimeException, FTPConnectionClosedRuntimeException, LoginFailedRuntimeException, DirectoryChangeFailedRuntimeException, CopyStreamRuntimeException, RefusedConnectionRuntimeException, IORuntimeException {
        final int NUM_XML_FILES = 2;
        final String META_XML_SUFFIX = "_meta.xml";
        final String FILES_XML_SUFFIX = "_files.xml";
        final String username = JavaCIPUnknownScope.getUsername();
        final String password = JavaCIPUnknownScope.getPassword();
        if (JavaCIPUnknownScope.getFtpServer() == null) {
            throw new IllegalStateRuntimeException("ftp server not set");
        }
        if (JavaCIPUnknownScope.getFtpPath() == null) {
            throw new IllegalStateRuntimeException("ftp path not set");
        }
        if (username == null) {
            throw new IllegalStateRuntimeException("username not set");
        }
        if (password == null) {
            throw new IllegalStateRuntimeException("password not set");
        }
        final String metaXmlString = JavaCIPUnknownScope.serializeDocument(JavaCIPUnknownScope.getMetaDocument());
        final String filesXmlString = JavaCIPUnknownScope.serializeDocument(JavaCIPUnknownScope.getFilesDocument());
        final byte[] metaXmlBytes = metaXmlString.getBytes();
        final byte[] filesXmlBytes = filesXmlString.getBytes();
        final int metaXmlLength = metaXmlBytes.length;
        final int filesXmlLength = filesXmlBytes.length;
        final Collection files = JavaCIPUnknownScope.getFiles();
        final int totalFiles = NUM_XML_FILES + files.size();
        final String[] fileNames = new String[totalFiles];
        final long[] fileSizes = new long[totalFiles];
        final String metaXmlName = JavaCIPUnknownScope.getIdentifier() + META_XML_SUFFIX;
        fileNames[0] = metaXmlName;
        fileSizes[0] = metaXmlLength;
        final String filesXmlName = JavaCIPUnknownScope.getIdentifier() + FILES_XML_SUFFIX;
        fileNames[1] = filesXmlName;
        fileSizes[1] = filesXmlLength;
        int j = 2;
        for (Iterator i = files.iterator(); i.hasNext(); ) {
            final ArchiveFile f = (ArchiveFile) i.next();
            fileNames[j] = f.getRemoteFileName();
            fileSizes[j] = f.getFileSize();
            j++;
        }
        for (int i = 0; i < fileSizes.length; i++) {
            JavaCIPUnknownScope._fileNames2Progress.put(fileNames[i], new UploadFileProgress(fileSizes[i]));
            JavaCIPUnknownScope._totalUploadSize += fileSizes[i];
        }
        FTPClient ftp = new FTPClient();
        try {
            if (JavaCIPUnknownScope.isCancelled()) {
                return;
            }
            ftp.enterLocalPassiveMode();
            if (JavaCIPUnknownScope.isCancelled()) {
                return;
            }
            ftp.connect(JavaCIPUnknownScope.getFtpServer());
            final int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new RefusedConnectionRuntimeException(JavaCIPUnknownScope.getFtpServer() + "refused FTP connection");
            }
            if (JavaCIPUnknownScope.isCancelled()) {
                return;
            }
            if (!ftp.login(username, password)) {
                throw new LoginFailedRuntimeException();
            }
            try {
                if (!ftp.changeWorkingDirectory(JavaCIPUnknownScope.getFtpPath())) {
                    if (!JavaCIPUnknownScope.isFtpDirPreMade() && !ftp.makeDirectory(JavaCIPUnknownScope.getFtpPath())) {
                        throw new DirectoryChangeFailedRuntimeException();
                    }
                    if (JavaCIPUnknownScope.isCancelled()) {
                        return;
                    }
                    if (!ftp.changeWorkingDirectory(JavaCIPUnknownScope.getFtpPath())) {
                        throw new DirectoryChangeFailedRuntimeException();
                    }
                }
                if (JavaCIPUnknownScope.isCancelled()) {
                    return;
                }
                JavaCIPUnknownScope.connected();
                JavaCIPUnknownScope.uploadFile(metaXmlName, new ByteArrayInputStream(metaXmlBytes), ftp);
                JavaCIPUnknownScope.uploadFile(filesXmlName, new ByteArrayInputStream(filesXmlBytes), ftp);
                if (JavaCIPUnknownScope.isCancelled()) {
                    return;
                }
                ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
                for (final Iterator i = files.iterator(); i.hasNext(); ) {
                    final ArchiveFile f = (ArchiveFile) i.next();
                    JavaCIPUnknownScope.uploadFile(f.getRemoteFileName(), new FileInputStream(f.getIOFile()), ftp);
                }
            } catch (InterruptedIORuntimeException ioe) {
                return;
            } finally {
                ftp.logout();
            }
        } finally {
            try {
                ftp.disconnect();
            } catch (IORuntimeException e) {
            }
        }
        if (JavaCIPUnknownScope.isCancelled()) {
            return;
        }
        JavaCIPUnknownScope.checkinStarted();
        if (JavaCIPUnknownScope.isCancelled()) {
            return;
        }
        JavaCIPUnknownScope.checkin();
        if (JavaCIPUnknownScope.isCancelled()) {
            return;
        }
        JavaCIPUnknownScope.checkinCompleted();
    }
}
