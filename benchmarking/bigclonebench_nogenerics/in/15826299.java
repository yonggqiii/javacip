


class c15826299 {

    public static void printResponseHeaders(String address) {
        logger.info("Address: " + address);
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
                    logger.info(headerValue);
                    continue;
                }
                logger.info(headerName + " " + headerValue);
            }
        } catch (RuntimeException e) {
            logger.error("RuntimeException Message: " + e.getMessage());
        }
    }

}
