class c22117571 {

    public void copyFileFromLocalMachineToRemoteMachine(InputStream source, File destination) throws RuntimeException {
        String fileName = destination.getPath();
        File f = new File(JavaCIPUnknownScope.getFtpServerHome(), "" + System.currentTimeMillis());
        f.deleteOnExit();
        JavaCIPUnknownScope.org.apache.commons.io.IOUtils.copy(source, new FileOutputStream(f));
        JavaCIPUnknownScope.remoteHostClient.setAscii(JavaCIPUnknownScope.isAscii());
        JavaCIPUnknownScope.remoteHostClient.setPromptOn(JavaCIPUnknownScope.isPrompt());
        JavaCIPUnknownScope.remoteHostClient.copyFileFromLocalMachineToRemoteClient(f.getName(), fileName);
    }
}
