


class c23677130 {

	public FTPClient sample3c(String ftpserver, int ftpport, String proxyserver, int proxyport, String username, String password) throws SocketRuntimeException, IORuntimeException {
		FTPHTTPClient ftpClient = new FTPHTTPClient(proxyserver, proxyport);
		ftpClient.setDefaultPort(ftpport);
		ftpClient.connect(ftpserver);
		ftpClient.login(username, password);
		return ftpClient;
	}

}
