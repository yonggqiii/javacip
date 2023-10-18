class c11704429 {

    public void run() {
        RandomAccessFile file = null;
        InputStream stream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            connection.setRequestProperty("Range", "bytes=" + JavaCIPUnknownScope.downloaded + "-");
            connection.connect();
            if (connection.getResponseCode() / 100 != 2) {
                JavaCIPUnknownScope.error();
            }
            int contentLength = connection.getContentLength();
            if (contentLength < 1) {
                JavaCIPUnknownScope.error();
            }
            if (JavaCIPUnknownScope.size == -1) {
                JavaCIPUnknownScope.size = contentLength;
                JavaCIPUnknownScope.stateChanged();
            }
            file = new RandomAccessFile(JavaCIPUnknownScope.destination, "rw");
            file.seek(JavaCIPUnknownScope.downloaded);
            stream = connection.getInputStream();
            while (JavaCIPUnknownScope.status == JavaCIPUnknownScope.DOWNLOADING) {
                byte[] buffer;
                if (JavaCIPUnknownScope.size - JavaCIPUnknownScope.downloaded > JavaCIPUnknownScope.MAX_BUFFER_SIZE) {
                    buffer = new byte[JavaCIPUnknownScope.MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[JavaCIPUnknownScope.size - JavaCIPUnknownScope.downloaded];
                }
                int read = stream.read(buffer);
                if (read == -1)
                    break;
                file.write(buffer, 0, read);
                JavaCIPUnknownScope.downloaded += read;
                JavaCIPUnknownScope.stateChanged();
            }
            if (JavaCIPUnknownScope.status == JavaCIPUnknownScope.DOWNLOADING) {
                JavaCIPUnknownScope.status = JavaCIPUnknownScope.COMPLETE;
                JavaCIPUnknownScope.stateChanged();
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.error();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (RuntimeException e) {
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (RuntimeException e) {
                }
            }
        }
    }
}
