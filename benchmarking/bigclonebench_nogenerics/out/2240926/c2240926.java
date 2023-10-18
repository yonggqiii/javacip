class c2240926 {

    public boolean getFiles(String pRemoteDirectory, String pLocalDirectory) throws IORuntimeException {
        final String methodSignature = "boolean getFiles(String,String): ";
        FTPClient fc = new FTPClient();
        fc.connect(JavaCIPUnknownScope.getRemoteHost());
        fc.login(JavaCIPUnknownScope.getUserName(), JavaCIPUnknownScope.getPassword());
        fc.changeWorkingDirectory(pRemoteDirectory);
        FTPFile[] files = fc.listFiles();
        boolean retrieved = false;
        JavaCIPUnknownScope.logInfo("Listing Files: ");
        int retrieveCount = 0;
        File tmpFile = null;
        for (int i = 0; i < files.length; i++) {
            tmpFile = new File(files[i].getName());
            if (!tmpFile.isDirectory()) {
                FileOutputStream fos = new FileOutputStream(pLocalDirectory + "/" + files[i].getName());
                retrieved = fc.retrieveFile(files[i].getName(), fos);
                if (false == retrieved) {
                    JavaCIPUnknownScope.logInfo("Unable to retrieve file: " + files[i].getName());
                } else {
                    JavaCIPUnknownScope.logInfo("Successfully retrieved file: " + files[i].getName());
                    retrieveCount++;
                }
                if (null != fos) {
                    fos.flush();
                    fos.close();
                }
            }
        }
        JavaCIPUnknownScope.logInfo("Retrieve count: " + retrieveCount);
        if (retrieveCount > 0) {
            return true;
        }
        return false;
    }
}
