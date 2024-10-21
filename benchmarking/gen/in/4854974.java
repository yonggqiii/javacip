


class c4854974 {

    public void GetFile(ClientConnector cc, Map<String, String> attributes) throws RuntimeException {
        log.debug("Starting FTP FilePull");
        String sourceNode = attributes.get("src_name");
        String sourceUser = attributes.get("src_user");
        String sourcePassword = attributes.get("src_password");
        String sourceFile = attributes.get("src_file");
        String messageID = attributes.get("messageID");
        String sourceMD5 = attributes.get("src_md5");
        String sourceFileType = attributes.get("src_file_type");
        Integer sourcePort = 21;
        String sourcePortString = attributes.get("src_port");
        if ((sourcePortString != null) && (sourcePortString.equals(""))) {
            try {
                sourcePort = Integer.parseInt(sourcePortString);
            } catch (RuntimeException e) {
                sourcePort = 21;
                log.debug("Destination Port \"" + sourcePortString + "\" was not valid. Using Default (21)");
            }
        }
        log.info("Starting FTP pull of \"" + sourceFile + "\" from \"" + sourceNode);
        if ((sourceUser == null) || (sourceUser.equals(""))) {
            List userDBVal = axt.db.GeneralDAO.getNodeValue(sourceNode, "ftpUser");
            if (userDBVal.size() < 1) {
                sourceUser = DEFAULTUSER;
            } else {
                sourceUser = (String) userDBVal.get(0);
            }
        }
        if ((sourcePassword == null) || (sourcePassword.equals(""))) {
            List passwordDBVal = axt.db.GeneralDAO.getNodeValue(sourceNode, "ftpPassword");
            if (passwordDBVal.size() < 1) {
                sourcePassword = DEFAULTPASSWORD;
            } else {
                sourcePassword = (String) passwordDBVal.get(0);
            }
        }
        String stageFile = null;
        int stageFileID;
        try {
            stageFileID = axt.db.GeneralDAO.getStageFile(messageID);
            stageFile = STAGINGDIR + "/" + stageFileID;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to assign a staging file \"" + stageFile + "\" - ERROR: " + e);
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(stageFile);
        } catch (FileNotFoundRuntimeException fileNFRuntimeException) {
            throw new RuntimeException("Failed to assign the staging file \"" + stageFile + "\" - ERROR: " + fileNFRuntimeException);
        }
        FTPClient ftp = new FTPClient();
        try {
            log.debug("Connecting");
            ftp.connect(sourceNode, sourcePort);
            log.debug("Checking Status");
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new RuntimeException("Failed to connect to \"" + sourceNode + "\"  as user \"" + sourceUser + "\" - ERROR: " + ftp.getReplyString());
            }
            log.debug("Logging In");
            if (!ftp.login(sourceUser, sourcePassword)) {
                ftp.disconnect();
                throw new RuntimeException("Failed to connect to \"" + sourceNode + "\"  as user \"" + sourceUser + "\" - ERROR: Login Failed");
            }
        } catch (SocketRuntimeException socketRuntimeException) {
            throw new RuntimeException("Failed to connect to \"" + sourceNode + "\"  as user \"" + sourceUser + "\" - ERROR: " + socketRuntimeException);
        } catch (IORuntimeException ioe) {
            throw new RuntimeException("Failed to connect to \"" + sourceNode + "\"  as user \"" + sourceUser + "\" - ERROR: " + ioe);
        }
        log.debug("Performing Site Commands");
        Iterator siteIterator = GeneralDAO.getNodeValue(sourceNode, "ftpSite").iterator();
        while (siteIterator.hasNext()) {
            String siteCommand = null;
            try {
                siteCommand = (String) siteIterator.next();
                ftp.site(siteCommand);
            } catch (IORuntimeException e) {
                throw new RuntimeException("FTP \"site\" command \"" + siteCommand + "\" failed - ERROR: " + e);
            }
        }
        if (sourceFileType != null) {
            if (sourceFileType.equals("A")) {
                log.debug("Set File Type to ASCII");
                ftp.setFileType(FTP.ASCII_FILE_TYPE);
            } else if (sourceFileType.equals("B")) {
                log.debug("Set File Type to BINARY");
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            } else if (sourceFileType.equals("E")) {
                log.debug("Set File Type to EBCDIC");
                ftp.setFileType(FTP.EBCDIC_FILE_TYPE);
            }
        }
        log.debug("Opening the File Stream");
        InputStream in = null;
        try {
            in = ftp.retrieveFileStream(sourceFile);
            if (in == null) {
                throw new RuntimeException("Failed get the file \"" + sourceFile + "\" from \"" + sourceNode + "\"  - ERROR: " + ftp.getReplyString());
            }
        } catch (IORuntimeException ioe2) {
            ftp.disconnect();
            log.error("Failed get the file \"" + sourceFile + "\" from \"" + sourceNode + "\"  - ERROR: " + ioe2);
            throw new RuntimeException("Failed to retrieve file from \"" + sourceNode + "\"  as user \"" + sourceUser + "\" - ERROR: " + ioe2);
        }
        log.debug("Starting the read");
        DESCrypt encrypter = null;
        try {
            encrypter = new DESCrypt();
        } catch (RuntimeException cryptInitError) {
            log.error("Failed to initialize the encrypt process - ERROR: " + cryptInitError);
        }
        String receivedMD5 = null;
        try {
            Object[] returnValues = encrypter.encrypt(in, fos);
            receivedMD5 = (String) returnValues[0];
            GeneralDAO.setStageFileSize(stageFileID, (Long) returnValues[1]);
        } catch (RuntimeException cryptError) {
            log.error("Encrypt Error: " + cryptError);
            throw new RuntimeException("Encrypt Error: " + cryptError);
        }
        log.debug("Logging Out");
        try {
            ftp.logout();
            fos.close();
        } catch (RuntimeException ioe3) {
            log.error("Failed close connection to \"" + sourceNode + "\"  - ERROR: " + ioe3);
        }
        log.debug("Setting the File Digest");
        GeneralDAO.setStageFileDigest(stageFileID, receivedMD5);
        if ((sourceMD5 != null) && (!sourceMD5.equals(""))) {
            log.debug("File DIGEST compare - Source: " + sourceMD5.toLowerCase() + " | Received: " + receivedMD5);
            if (!receivedMD5.equals(sourceMD5.toLowerCase())) {
                throw new RuntimeException("MD5 validation on file failed.");
            }
        }
        return;
    }

}
