class c21679395 {

    public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType) throws IORuntimeException {
        JavaCIPUnknownScope.LOGGER.debug("DOWNLOAD - Send content: " + JavaCIPUnknownScope.realFile.getAbsolutePath());
        JavaCIPUnknownScope.LOGGER.debug("Output stream: " + out.toString());
        if (ServerConfiguration.isDynamicSEL()) {
            JavaCIPUnknownScope.LOGGER.error("IS DINAMIC SEL????");
        } else {
        }
        if (".tokens".equals(JavaCIPUnknownScope.realFile.getName()) || ".response".equals(JavaCIPUnknownScope.realFile.getName()) || ".request".equals(JavaCIPUnknownScope.realFile.getName()) || JavaCIPUnknownScope.isAllowedClient) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(JavaCIPUnknownScope.realFile);
                int bytes = IOUtils.copy(in, out);
                JavaCIPUnknownScope.LOGGER.debug("System resource or Allowed Client wrote bytes:  " + bytes);
                out.flush();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.LOGGER.error("Error while downloading over encryption system " + JavaCIPUnknownScope.realFile.getName() + " file", e);
            } finally {
                IOUtils.closeQuietly(in);
            }
        } else {
        }
    }
}
