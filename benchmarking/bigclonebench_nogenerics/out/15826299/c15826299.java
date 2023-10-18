class c15826299 {

    public static void printResponseHeaders(String address) {
        JavaCIPUnknownScope.logger.info("Address: " + address);
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            for (int i = 0; ; i++) {
                String headerName = conn.getHeaderFieldKey(i);
                String headerValue = conn.getHeaderField(i);
                if (headerName == null && headerValue == null) {
                    break;
                }
                if (headerName == null) {
                    JavaCIPUnknownScope.logger.info(headerValue);
                    continue;
                }
                JavaCIPUnknownScope.logger.info(headerName + " " + headerValue);
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error("RuntimeException Message: " + e.getMessage());
        }
    }
}
