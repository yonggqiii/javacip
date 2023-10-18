class c15946011 {

    public long calculateResponseTime(Proxy proxy) {
        try {
            JavaCIPUnknownScope.LOGGER.debug("Test network response time for " + JavaCIPUnknownScope.RESPONSE_TEST_URL);
            URL urlForTest = new URL(JavaCIPUnknownScope.REACH_TEST_URL);
            URLConnection testConnection = urlForTest.openConnection(proxy);
            long startTime = System.currentTimeMillis();
            testConnection.connect();
            testConnection.connect();
            testConnection.connect();
            testConnection.connect();
            testConnection.connect();
            long endTime = System.currentTimeMillis();
            long averageResponseTime = (endTime - startTime) / 5;
            JavaCIPUnknownScope.LOGGER.debug("Average access time in ms : " + averageResponseTime);
            return averageResponseTime;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
        }
        return -1;
    }
}
