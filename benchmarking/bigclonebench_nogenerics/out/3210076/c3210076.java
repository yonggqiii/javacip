class c3210076 {

    private void readHomePage(ITestThread testThread) throws IORuntimeException {
        if (null == testThread) {
            throw new IllegalArgumentRuntimeException("Test thread may not be null.");
        }
        final InputStream urlIn = new URL(JavaCIPUnknownScope.testUrl).openStream();
        final int availableBytes = urlIn.available();
        if (0 == availableBytes) {
            throw new IllegalStateRuntimeException("Zero bytes on target host.");
        }
        JavaCIPUnknownScope.in = new BufferedReader(new InputStreamReader(urlIn));
        String line;
        while (null != JavaCIPUnknownScope.in && null != (line = JavaCIPUnknownScope.in.readLine())) {
            JavaCIPUnknownScope.page.append(line);
            JavaCIPUnknownScope.page.append('\n');
            if (0 != JavaCIPUnknownScope.lineDelay) {
                JavaCIPUnknownScope.OS.sleep(JavaCIPUnknownScope.lineDelay);
            }
            if (testThread.isActionStopped()) {
                break;
            }
        }
    }
}
