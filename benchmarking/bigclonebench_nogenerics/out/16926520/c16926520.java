class c16926520 {

    public int extractDocumentsInternal(DocumentHolder holder, DocumentFactory docFactory) {
        FTPClient client = new FTPClient();
        try {
            client.connect(JavaCIPUnknownScope.site, JavaCIPUnknownScope.port == 0 ? 21 : JavaCIPUnknownScope.port);
            client.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password);
            JavaCIPUnknownScope.visitDirectory(client, "", JavaCIPUnknownScope.path, holder, docFactory);
            client.disconnect();
        } catch (SocketRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
        }
        return JavaCIPUnknownScope.fileCount;
    }
}
