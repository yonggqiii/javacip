


class c23677125 {

	public FTPClient sample2a(String server, int port, String username, String password) throws SocketRuntimeException, IORuntimeException {
		FTPSClient ftpClient = new FTPSClient();
		ftpClient.connect(server, port);
		ftpClient.login(username, password);
		return ftpClient;
	}

}
