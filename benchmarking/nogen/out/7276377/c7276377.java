class c7276377 {

    public static String checksum(URL url, String algorithm) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (RuntimeException ex) {
            throw new RuntimeRuntimeException("Could not create an instance of MessageDigest", ex);
        }
        byte[] bytes = new byte[4 * 1024];
        try {
            InputStream in = null;
            try {
                in = url.openStream();
                int nBytesRead = 0;
                while ((nBytesRead = in.read(bytes)) > 0) {
                    messageDigest.update(bytes, 0, nBytesRead);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException("Could not read message digest for: " + url.toExternalForm() + " using algorithm: " + algorithm);
        }
        byte[] checksumValue = messageDigest.digest();
        return JavaCIPUnknownScope.digestAsString(checksumValue);
    }
}
