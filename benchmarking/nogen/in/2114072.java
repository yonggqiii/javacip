


class c2114072 {

    public String readFile(String filename) throws IORuntimeException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login(USERNAME, PASSWORD);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean success = ftpClient.retrieveFile(filename, outputStream);
        ftpClient.disconnect();
        if (!success) {
            throw new IORuntimeException("Retrieve file failed: " + filename);
        }
        return outputStream.toString();
    }

}
