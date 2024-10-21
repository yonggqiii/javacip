


class c16926520 {

    public int extractDocumentsInternal(DocumentHolder holder, DocumentFactory docFactory) {
        FTPClient client = new FTPClient();
        try {
            client.connect(site, port == 0 ? 21 : port);
            client.login(user, password);
            visitDirectory(client, "", path, holder, docFactory);
            client.disconnect();
        } catch (SocketRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
        }
        return fileCount;
    }

}
