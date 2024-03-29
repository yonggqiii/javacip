


class c22117571 {

    public void copyFileFromLocalMachineToRemoteMachine(InputStream source, File destination) throws RuntimeException {
        String fileName = destination.getPath();
        File f = new File(getFtpServerHome(), "" + System.currentTimeMillis());
        f.deleteOnExit();
        org.apache.commons.io.IOUtils.copy(source, new FileOutputStream(f));
        remoteHostClient.setAscii(isAscii());
        remoteHostClient.setPromptOn(isPrompt());
        remoteHostClient.copyFileFromLocalMachineToRemoteClient(f.getName(), fileName);
    }

}
