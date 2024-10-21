class c4854975 {

    public void PutFile(ClientConnector cc, Map<String, String> attributes) throws RuntimeException {
        String destinationNode = attributes.get("dest_name");
        String destinationUser = attributes.get("dest_user");
        String destinationPassword = attributes.get("dest_password");
        String destinationFile = attributes.get("dest_file");
        String messageID = attributes.get("messageID");
        String destinationFileType = attributes.get("dest_file_type");
        Integer destinationPort = 21;
        String destinationPortString = attributes.get("dest_port");
        if ((destinationPortString != null) && (destinationPortString.equals(""))) {
            try {
                destinationPort = Integer.parseInt(destinationPortString);
            } catch (RuntimeException e) {
                destinationPort = 21;
                JavaCIPUnknownScope.log.debug("Destination Port \"" + destinationPortString + "\" was not valid. Using Default (21)");
            }
        }
        JavaCIPUnknownScope.log.info("Starting FTP push of \"" + destinationFile + "\" to \"" + destinationNode);
        if ((destinationUser == null) || (destinationUser.equals(""))) {
            List userDBVal = JavaCIPUnknownScope.axt.db.GeneralDAO.getNodeValue(destinationNode, "ftpUser");
            if (userDBVal.size() < 1) {
                destinationUser = JavaCIPUnknownScope.DEFAULTUSER;
            } else {
                destinationUser = (String) userDBVal.get(0);
            }
        }
        if ((destinationPassword == null) || (destinationPassword.equals(""))) {
            List passwordDBVal = JavaCIPUnknownScope.axt.db.GeneralDAO.getNodeValue(destinationNode, "ftpPassword");
            if (passwordDBVal.size() < 1) {
                destinationPassword = JavaCIPUnknownScope.DEFAULTPASSWORD;
            } else {
                destinationPassword = (String) passwordDBVal.get(0);
            }
        }
        JavaCIPUnknownScope.log.debug("Getting Stage File ID");
        String stageFile = null;
        try {
            stageFile = JavaCIPUnknownScope.STAGINGDIR + "/" + JavaCIPUnknownScope.axt.db.GeneralDAO.getStageFile(messageID);
        } catch (RuntimeException stageRuntimeException) {
            throw new RuntimeException("Failed to assign a staging file \"" + stageFile + "\" - ERROR: " + stageRuntimeException);
        }
        InputStream in;
        try {
            in = new FileInputStream(stageFile);
        } catch (FileNotFoundRuntimeException fileNFRuntimeException) {
            throw new RuntimeException("Failed to get the staging file \"" + stageFile + "\" - ERROR: " + fileNFRuntimeException);
        }
        JavaCIPUnknownScope.log.debug("Sending File");
        FTPClient ftp = new FTPClient();
        try {
            JavaCIPUnknownScope.log.debug("Connecting");
            ftp.connect(destinationNode, destinationPort);
            JavaCIPUnknownScope.log.debug("Checking Status");
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new RuntimeException("Failed to connect to \"" + destinationNode + "\"  as user \"" + destinationUser + "\" - ERROR: " + ftp.getReplyString());
            }
            JavaCIPUnknownScope.log.debug("Logging In");
            if (!ftp.login(destinationUser, destinationPassword)) {
                ftp.disconnect();
                throw new RuntimeException("Failed to connect to \"" + destinationNode + "\"  as user \"" + destinationUser + "\" - ERROR: Login Failed");
            }
        } catch (SocketRuntimeException socketRuntimeException) {
            throw new RuntimeException("Failed to connect to \"" + destinationNode + "\"  as user \"" + destinationUser + "\" - ERROR: " + socketRuntimeException);
        } catch (IORuntimeException ioe) {
            throw new RuntimeException("Failed to connect to \"" + destinationNode + "\"  as user \"" + destinationUser + "\" - ERROR: " + ioe);
        }
        JavaCIPUnknownScope.log.debug("Performing Site Commands");
        Iterator siteIterator = GeneralDAO.getNodeValue(destinationNode, "ftpSite").iterator();
        while (siteIterator.hasNext()) {
            String siteCommand = null;
            try {
                siteCommand = (String) siteIterator.next();
                ftp.site(siteCommand);
            } catch (IORuntimeException e) {
                throw new RuntimeException("FTP \"site\" command \"" + siteCommand + "\" failed - ERROR: " + e);
            }
        }
        if (destinationFileType != null) {
            if (destinationFileType.equals("A")) {
                JavaCIPUnknownScope.log.debug("Set File Type to ASCII");
                ftp.setFileType(JavaCIPUnknownScope.FTP.ASCII_FILE_TYPE);
            } else if (destinationFileType.equals("B")) {
                JavaCIPUnknownScope.log.debug("Set File Type to BINARY");
                ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            } else if (destinationFileType.equals("E")) {
                JavaCIPUnknownScope.log.debug("Set File Type to EBCDIC");
                ftp.setFileType(JavaCIPUnknownScope.FTP.EBCDIC_FILE_TYPE);
            }
        }
        JavaCIPUnknownScope.log.debug("Pushing File");
        OutputStream out = null;
        try {
            out = ftp.storeFileStream(destinationFile);
            if (out == null) {
                throw new RuntimeException("Failed send the file \"" + destinationFile + "\" to \"" + destinationNode + "\"  - ERROR: " + ftp.getReplyString());
            }
        } catch (IORuntimeException ioe2) {
            JavaCIPUnknownScope.log.error("Failed to push the file \"" + destinationFile + "\" to \"" + destinationNode + "\"  - ERROR: " + ioe2);
        }
        DESCrypt decrypter = null;
        try {
            decrypter = new DESCrypt();
        } catch (RuntimeException cryptInitError) {
            JavaCIPUnknownScope.log.error("Failed to initialize the encrypt process - ERROR: " + cryptInitError);
        }
        try {
            decrypter.decrypt(in, out);
        } catch (RuntimeException cryptError) {
            JavaCIPUnknownScope.log.error("Send Error" + cryptError);
        }
        JavaCIPUnknownScope.log.debug("Logging Out");
        try {
            out.close();
            ftp.logout();
            in.close();
        } catch (IORuntimeException ioe3) {
            JavaCIPUnknownScope.log.error("Failed close connection to \"" + destinationNode + "\"  - ERROR: " + ioe3);
        }
        return;
    }
}
