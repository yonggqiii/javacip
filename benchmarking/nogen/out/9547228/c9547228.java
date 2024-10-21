class c9547228 {

    public void run() {
        String masterUrl = "http://localhost:" + JavaCIPUnknownScope.masterJetty.getLocalPort() + "/solr/replication?command=" + ReplicationHandler.CMD_BACKUP;
        URL url;
        InputStream stream = null;
        try {
            url = new URL(masterUrl);
            stream = url.openStream();
            stream.close();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.fail = e.getMessage();
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
