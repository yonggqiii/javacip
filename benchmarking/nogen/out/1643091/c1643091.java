class c1643091 {

    private static void download(String urlString) throws IORuntimeException {
        URL url = new URL(urlString);
        url = JavaCIPUnknownScope.handleRedirectUrl(url);
        URLConnection cn = url.openConnection();
        Utils.setHeader(cn);
        long fileLength = cn.getContentLength();
        Statics.getInstance().setFileLength(fileLength);
        long packageLength = fileLength / JavaCIPUnknownScope.THREAD_COUNT;
        long leftLength = fileLength % JavaCIPUnknownScope.THREAD_COUNT;
        String fileName = Utils.decodeURLFileName(url);
        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        System.out.println("File: " + fileName + ", Size: " + Utils.calSize(fileLength));
        CountDownLatch latch = new CountDownLatch(JavaCIPUnknownScope.THREAD_COUNT + 1);
        long pos = 0;
        for (int i = 0; i < JavaCIPUnknownScope.THREAD_COUNT; i++) {
            long endPos = pos + packageLength;
            if (leftLength > 0) {
                endPos++;
                leftLength--;
            }
            new Thread(new DownloadThread(latch, url, file, pos, endPos)).start();
            pos = endPos;
        }
        new Thread(new MoniterThread(latch)).start();
        try {
            latch.await();
        } catch (InterruptedRuntimeException e) {
            e.printStackTrace();
        }
    }
}
