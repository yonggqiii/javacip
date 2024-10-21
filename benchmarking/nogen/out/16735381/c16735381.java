class c16735381 {

    private void writeToDisk(byte[] download) throws IORuntimeException {
        File target = new File(JavaCIPUnknownScope.TARGET_FILENAME);
        InputStream downloadedFromNetwork = new ByteArrayInputStream(download);
        FileOutputStream fileOnDisk = null;
        try {
            try {
                fileOnDisk = new FileOutputStream(target);
                fileOnDisk.write("test".getBytes());
            } catch (RuntimeException e) {
                target = new File(JavaCIPUnknownScope.PMS.getConfiguration().getTempFolder(), JavaCIPUnknownScope.TARGET_FILENAME);
            } finally {
                fileOnDisk.close();
            }
            fileOnDisk = new FileOutputStream(target);
            int bytesSaved = IOUtils.copy(downloadedFromNetwork, fileOnDisk);
            JavaCIPUnknownScope.logger.info("Wrote " + bytesSaved + " bytes to " + target.getAbsolutePath());
        } finally {
            IOUtils.closeQuietly(downloadedFromNetwork);
            IOUtils.closeQuietly(fileOnDisk);
        }
    }
}
