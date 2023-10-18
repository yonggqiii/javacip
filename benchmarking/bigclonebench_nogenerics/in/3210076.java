


class c3210076 {

    private void readHomePage(ITestThread testThread) throws IORuntimeException {
        if (null == testThread) {
            throw new IllegalArgumentRuntimeException("Test thread may not be null.");
        }
        final InputStream urlIn = new URL(testUrl).openStream();
        final int availableBytes = urlIn.available();
        if (0 == availableBytes) {
            throw new IllegalStateRuntimeException("Zero bytes on target host.");
        }
        in = new BufferedReader(new InputStreamReader(urlIn));
        String line;
        while (null != in && null != (line = in.readLine())) {
            page.append(line);
            page.append('\n');
            if (0 != lineDelay) {
                OS.sleep(lineDelay);
            }
            if (testThread.isActionStopped()) {
                break;
            }
        }
    }

}
