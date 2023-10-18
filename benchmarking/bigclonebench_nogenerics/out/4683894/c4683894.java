class c4683894 {

    public void run() {
        if (JavaCIPUnknownScope.currentNode == null || JavaCIPUnknownScope.currentNode.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select a genome to download first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String localFile = JavaCIPUnknownScope.parameter.getTemporaryFilesPath() + JavaCIPUnknownScope.currentNode;
        String remotePath = JavaCIPUnknownScope.NCBI_FTP_PATH + JavaCIPUnknownScope.currentPath;
        String remoteFile = remotePath + "/" + JavaCIPUnknownScope.currentNode;
        try {
            JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.NCBI_FTP_HOST);
            int reply = JavaCIPUnknownScope.ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                JavaCIPUnknownScope.ftp.disconnect();
                JOptionPane.showMessageDialog(null, "FTP server refused connection", "Error", JOptionPane.ERROR_MESSAGE);
            }
            JavaCIPUnknownScope.ftp.login("anonymous", "anonymous@big.ac.cn");
            JavaCIPUnknownScope.inProgress = true;
            JavaCIPUnknownScope.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            long size = JavaCIPUnknownScope.getFileSize(remotePath, JavaCIPUnknownScope.currentNode);
            if (size == -1)
                throw new FileNotFoundRuntimeException();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));
            BufferedInputStream in = new BufferedInputStream(JavaCIPUnknownScope.ftp.retrieveFileStream(remoteFile), JavaCIPUnknownScope.ftp.getBufferSize());
            byte[] b = new byte[1024];
            long bytesTransferred = 0;
            int tick = 0;
            int oldTick = 0;
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
                bytesTransferred += 1024;
                if ((tick = new Long(bytesTransferred * 100 / size).intValue()) > oldTick) {
                    JavaCIPUnknownScope.progressBar.setValue(tick < 100 ? tick : 99);
                    oldTick = tick;
                }
            }
            in.close();
            out.close();
            JavaCIPUnknownScope.ftp.completePendingCommand();
            JavaCIPUnknownScope.progressBar.setValue(100);
            JavaCIPUnknownScope.fileDownloaded = localFile;
            JOptionPane.showMessageDialog(null, "File successfully downloaded", "Congratulation!", JOptionPane.INFORMATION_MESSAGE);
            JavaCIPUnknownScope.ftp.logout();
        } catch (SocketRuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error occurs while trying to connect server", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundRuntimeException ex) {
            JOptionPane.showMessageDialog(null, "This file is not found on the server", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IORuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error occurs while fetching data", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            JavaCIPUnknownScope.inProgress = false;
            if (JavaCIPUnknownScope.ftp.isConnected()) {
                try {
                    JavaCIPUnknownScope.ftp.disconnect();
                } catch (IORuntimeException ioe) {
                }
            }
        }
    }
}
