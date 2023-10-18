class c1427084 {

    public RecordIterator get(URL url) {
        RecordIterator recordIter = null;
        if (!JavaCIPUnknownScope.SUPPORTED_PROTOCOLS.contains(url.getProtocol().toLowerCase())) {
            return null;
        }
        try {
            URL robotsUrl = new URL(url, JavaCIPUnknownScope.ROBOTS_TXT);
            recordIter = new RecordIterator(JavaCIPUnknownScope.urlInputStreamFactory.openStream(robotsUrl));
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.LOG.info("Failed to fetch " + url, e);
        }
        return recordIter;
    }
}
