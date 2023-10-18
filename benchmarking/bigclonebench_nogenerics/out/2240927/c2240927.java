class c2240927 {

    public boolean getFile(String pRemoteDirectory, String pLocalDirectory, String pFileName) throws IORuntimeException {
        FTPClient fc = new FTPClient();
        fc.connect(JavaCIPUnknownScope.getRemoteHost());
        fc.login(JavaCIPUnknownScope.getUserName(), JavaCIPUnknownScope.getPassword());
        fc.changeWorkingDirectory(pRemoteDirectory);
        String workingDirectory = fc.printWorkingDirectory();
        FileOutputStream fos = null;
        JavaCIPUnknownScope.logInfo("Connected to remote host=" + JavaCIPUnknownScope.getRemoteHost() + "; userName=" + JavaCIPUnknownScope.getUserName() + "; " + "; remoteDirectory=" + pRemoteDirectory + "; localDirectory=" + pLocalDirectory + "; workingDirectory=" + workingDirectory);
        try {
            fos = new FileOutputStream(pLocalDirectory + "/" + pFileName);
            boolean retrieved = fc.retrieveFile(pFileName, fos);
            if (true == retrieved) {
                JavaCIPUnknownScope.logInfo("Successfully retrieved file: " + pFileName);
            } else {
                JavaCIPUnknownScope.logError("Could not retrieve file: " + pFileName);
            }
            return retrieved;
        } finally {
            if (null != fos) {
                fos.flush();
                fos.close();
            }
        }
    }
}
