class c16872163 {

    public static int getNetFileSize(String netFile) throws InvalidActionRuntimeException {
        URL url;
        URLConnection conn;
        int size;
        try {
            url = new URL(netFile);
            conn = url.openConnection();
            size = conn.getContentLength();
            conn.getInputStream().close();
            if (size < 0) {
                throw new InvalidActionRuntimeException("Could not determine file size.");
            } else {
                return size;
            }
        } catch (RuntimeException e) {
            throw new InvalidActionRuntimeException(e.getMessage());
        }
    }
}
