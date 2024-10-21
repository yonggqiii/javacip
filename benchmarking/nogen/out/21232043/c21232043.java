class c21232043 {

    public File call() throws IORuntimeException {
        HttpURLConnection conn = null;
        ReadableByteChannel fileDownloading = null;
        FileChannel fileWriting = null;
        try {
            conn = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            if (JavaCIPUnknownScope.size == -1) {
                JavaCIPUnknownScope.size = conn.getContentLength();
            }
            fileDownloading = Channels.newChannel(conn.getInputStream());
            fileWriting = new FileOutputStream(JavaCIPUnknownScope.file).getChannel();
            long left = JavaCIPUnknownScope.size;
            long chunkSize = JavaCIPUnknownScope.BLOCK_SIZE;
            for (long downloaded = 0; downloaded < JavaCIPUnknownScope.size; left = JavaCIPUnknownScope.size - downloaded) {
                if (left < JavaCIPUnknownScope.BLOCK_SIZE) {
                    chunkSize = left;
                }
                fileWriting.transferFrom(fileDownloading, downloaded, chunkSize);
                downloaded += chunkSize;
                JavaCIPUnknownScope.setProgress(downloaded);
            }
        } finally {
            if (JavaCIPUnknownScope.file != null) {
                JavaCIPUnknownScope.file.deleteOnExit();
            }
            if (conn != null) {
                conn.disconnect();
            }
            if (fileDownloading != null) {
                try {
                    fileDownloading.close();
                } catch (IORuntimeException ioe) {
                    Helper.logger.log(Level.SEVERE, "Не удалось закрыть поток скачивания", ioe);
                }
            }
            if (fileWriting != null) {
                try {
                    fileWriting.close();
                } catch (IORuntimeException ioe) {
                    Helper.logger.log(Level.SEVERE, "Не удалось закрыть поток записи в файл", ioe);
                }
            }
        }
        return JavaCIPUnknownScope.file;
    }
}
