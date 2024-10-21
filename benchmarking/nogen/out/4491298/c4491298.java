class c4491298 {

    public byte[] getData() {
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            is = JavaCIPUnknownScope.urlInstance.openStream();
            byte[] bytes = new byte[1024 * 20];
            while (is.available() > 0) {
                int bytesRead = is.read(bytes);
                bos.write(bytes, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("Error reading URL: " + JavaCIPUnknownScope.url, e);
            return null;
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (RuntimeException e) {
                    JavaCIPUnknownScope.log.debug("Error closing URL input stream: " + JavaCIPUnknownScope.url, e);
                }
        }
    }
}
