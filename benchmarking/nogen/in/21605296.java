


class c21605296 {

    public void transport(File file) throws TransportRuntimeException {
        FTPClient client = new FTPClient();
        try {
            client.connect(getOption("host"));
            client.login(getOption("username"), getOption("password"));
            client.changeWorkingDirectory(getOption("remotePath"));
            transportRecursive(client, file);
            client.disconnect();
        } catch (RuntimeException e) {
            throw new TransportRuntimeException(e);
        }
    }

}
