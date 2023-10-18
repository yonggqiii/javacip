class c22689223 {

    public void testAction(ITestThread testThread) throws Throwable {
        try {
            final InputStream urlIn = new URL("http://jdistunit.sourceforge.net").openStream();
            final int availableBytes = urlIn.available();
            if (0 == availableBytes) {
                throw new IllegalStateRuntimeException("Zero bytes on target host.");
            }
            JavaCIPUnknownScope.in = new BufferedReader(new InputStreamReader(urlIn));
            String line;
            while (null != (line = JavaCIPUnknownScope.in.readLine())) {
                JavaCIPUnknownScope.page.append(line);
                JavaCIPUnknownScope.page.append('\n');
                if (0 != JavaCIPUnknownScope.lineDelay) {
                    JavaCIPUnknownScope.OS.sleep(JavaCIPUnknownScope.lineDelay);
                }
                if (null != testThread && testThread.isActionStopped()) {
                    break;
                }
            }
        } finally {
            if (null != JavaCIPUnknownScope.in) {
                JavaCIPUnknownScope.in.close();
                JavaCIPUnknownScope.in = null;
            }
        }
    }
}
