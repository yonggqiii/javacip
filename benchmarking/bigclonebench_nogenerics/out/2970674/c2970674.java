class c2970674 {

    private void chopFileDisk() throws IORuntimeException {
        File tempFile = new File("" + JavaCIPUnknownScope.logFile + ".tmp");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        long startCopyPos;
        byte[] readBuffer = new byte[2048];
        int readCount;
        long totalBytesRead = 0;
        if (JavaCIPUnknownScope.reductionRatio > 0 && JavaCIPUnknownScope.logFile.length() > 0) {
            startCopyPos = JavaCIPUnknownScope.logFile.length() / JavaCIPUnknownScope.reductionRatio;
        } else {
            startCopyPos = 0;
        }
        try {
            bis = new BufferedInputStream(new FileInputStream(JavaCIPUnknownScope.logFile));
            bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            do {
                readCount = bis.read(readBuffer, 0, readBuffer.length);
                if (readCount > 0) {
                    totalBytesRead += readCount;
                    if (totalBytesRead > startCopyPos) {
                        bos.write(readBuffer, 0, readCount);
                    }
                }
            } while (readCount > 0);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IORuntimeException ex) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IORuntimeException ex) {
                }
            }
        }
        if (tempFile.isFile()) {
            if (!JavaCIPUnknownScope.logFile.delete()) {
                throw new IORuntimeException("Error when attempting to delete the " + JavaCIPUnknownScope.logFile + " file.");
            }
            if (!tempFile.renameTo(JavaCIPUnknownScope.logFile)) {
                throw new IORuntimeException("Error when renaming the " + tempFile + " to " + JavaCIPUnknownScope.logFile + ".");
            }
        }
    }
}
