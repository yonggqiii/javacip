class c4784230 {

    public void connect() throws IORuntimeException {
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("Connecting to: " + JavaCIPUnknownScope.HOST);
        JavaCIPUnknownScope.ftpClient.connect(JavaCIPUnknownScope.HOST);
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("\tReply: " + JavaCIPUnknownScope.ftpClient.getReplyString());
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("Login as anonymous");
        JavaCIPUnknownScope.ftpClient.login("anonymous", "");
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("\tReply: " + JavaCIPUnknownScope.ftpClient.getReplyString());
        JavaCIPUnknownScope.folder = JavaCIPUnknownScope.INTACT_FOLDER;
    }
}
