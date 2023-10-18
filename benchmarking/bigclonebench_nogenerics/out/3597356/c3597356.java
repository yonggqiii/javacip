class c3597356 {

    public Boolean connect() throws RuntimeException {
        try {
            JavaCIPUnknownScope._ftpClient = new FTPClient();
            JavaCIPUnknownScope._ftpClient.connect(JavaCIPUnknownScope._url);
            JavaCIPUnknownScope._ftpClient.login(JavaCIPUnknownScope._username, JavaCIPUnknownScope._password);
            JavaCIPUnknownScope._rootPath = JavaCIPUnknownScope._ftpClient.printWorkingDirectory();
            return true;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Cannot connect to server.");
        }
    }
}
