class c19689457 {

    public synchronized void connect() throws FTPRuntimeException, IORuntimeException {
        if (JavaCIPUnknownScope.eventAggregator != null) {
            JavaCIPUnknownScope.eventAggregator.setConnId(JavaCIPUnknownScope.ftpClient.getId());
            JavaCIPUnknownScope.ftpClient.setMessageListener(JavaCIPUnknownScope.eventAggregator);
            JavaCIPUnknownScope.ftpClient.setProgressMonitor(JavaCIPUnknownScope.eventAggregator);
            JavaCIPUnknownScope.ftpClient.setProgressMonitorEx(JavaCIPUnknownScope.eventAggregator);
        }
        JavaCIPUnknownScope.statistics.clear();
        JavaCIPUnknownScope.configureClient();
        JavaCIPUnknownScope.log.debug("Configured client");
        JavaCIPUnknownScope.ftpClient.connect();
        JavaCIPUnknownScope.log.debug("Client connected");
        if (JavaCIPUnknownScope.masterContext.isAutoLogin()) {
            JavaCIPUnknownScope.log.debug("Logging in");
            JavaCIPUnknownScope.ftpClient.login(JavaCIPUnknownScope.masterContext.getUserName(), JavaCIPUnknownScope.masterContext.getPassword());
            JavaCIPUnknownScope.log.debug("Logged in");
            JavaCIPUnknownScope.configureTransferType(JavaCIPUnknownScope.masterContext.getContentType());
        } else {
            JavaCIPUnknownScope.log.debug("Manual login enabled");
        }
    }
}
