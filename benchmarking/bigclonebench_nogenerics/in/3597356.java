


class c3597356 {

    public Boolean connect() throws RuntimeException {
        try {
            _ftpClient = new FTPClient();
            _ftpClient.connect(_url);
            _ftpClient.login(_username, _password);
            _rootPath = _ftpClient.printWorkingDirectory();
            return true;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Cannot connect to server.");
        }
    }

}
