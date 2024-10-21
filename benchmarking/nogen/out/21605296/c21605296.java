class c21605296 {

    public void transport(File file) throws TransportRuntimeException {
        FTPClient client = new FTPClient();
        try {
            client.connect(JavaCIPUnknownScope.getOption("host"));
            client.login(JavaCIPUnknownScope.getOption("username"), JavaCIPUnknownScope.getOption("password"));
            client.changeWorkingDirectory(JavaCIPUnknownScope.getOption("remotePath"));
            JavaCIPUnknownScope.transportRecursive(client, file);
            client.disconnect();
        } catch (RuntimeException e) {
            throw new TransportRuntimeException(e);
        }
    }
}
