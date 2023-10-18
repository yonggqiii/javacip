class c8292297 {

    public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType) throws IOException {
        JavaCIPUnknownScope.LOGGER.debug("GET REQUEST OR RESPONSE - Send content: " + JavaCIPUnknownScope.file.getAbsolutePath());
        FileInputStream in = null;
        try {
            in = new FileInputStream(JavaCIPUnknownScope.file);
            int bytes = IOUtils.copy(in, out);
            JavaCIPUnknownScope.LOGGER.debug("wrote bytes:  " + bytes);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
