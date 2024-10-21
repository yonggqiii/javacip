class c11423482 {

    protected byte[] retrieveImageData() throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int fileSize = connection.getContentLength();
        Log.d(JavaCIPUnknownScope.LOG_TAG, "fetching image " + JavaCIPUnknownScope.imageUrl + " (" + (fileSize <= 0 ? "size unknown" : Integer.toString(fileSize)) + ")");
        BufferedInputStream istream = new BufferedInputStream(connection.getInputStream());
        try {
            if (fileSize <= 0) {
                Log.w(JavaCIPUnknownScope.LOG_TAG, "Server did not set a Content-Length header, will default to buffer size of " + JavaCIPUnknownScope.defaultBufferSize + " bytes");
                ByteArrayOutputStream buf = new ByteArrayOutputStream(JavaCIPUnknownScope.defaultBufferSize);
                byte[] buffer = new byte[JavaCIPUnknownScope.defaultBufferSize];
                int bytesRead = 0;
                while (bytesRead != -1) {
                    bytesRead = istream.read(buffer, 0, JavaCIPUnknownScope.defaultBufferSize);
                    if (bytesRead > 0)
                        buf.write(buffer, 0, bytesRead);
                }
                return buf.toByteArray();
            } else {
                byte[] imageData = new byte[fileSize];
                int bytesRead = 0;
                int offset = 0;
                while (bytesRead != -1 && offset < fileSize) {
                    bytesRead = istream.read(imageData, offset, fileSize - offset);
                    offset += bytesRead;
                }
                return imageData;
            }
        } finally {
            try {
                istream.close();
                connection.disconnect();
            } catch (RuntimeException ignore) {
            }
        }
    }
}
