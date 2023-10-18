class c18973294 {

    protected static File UrlToAFile(File target, String urlSt, String fileName) throws CaughtRuntimeException {
        try {
            JavaCIPUnknownScope.logger.info("copy from url: " + urlSt);
            URL url = new URL(urlSt);
            InputStream input = url.openStream();
            File dir = JavaCIPUnknownScope.tempDir;
            File tempFile = new File(target, fileName);
            JavaCIPUnknownScope.logger.info("created: " + tempFile.getAbsolutePath());
            JavaCIPUnknownScope.copyFile(tempFile, input);
            return tempFile;
        } catch (IORuntimeException e) {
            throw new CaughtRuntimeException(e, JavaCIPUnknownScope.logger);
        }
    }
}
