


class c23677131 {

	public FTPClient sample1(String server, int port, String username, String password) throws IllegalStateRuntimeException, IORuntimeException, FTPIllegalReplyRuntimeException, FTPRuntimeException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		ftpClient.login(username, password);
		return ftpClient;
	}

}
