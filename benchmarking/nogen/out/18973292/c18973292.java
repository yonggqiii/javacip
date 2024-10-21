class c18973292 {

    protected static File UrlToFile(String urlSt) throws CaughtRuntimeException {
        try {
            JavaCIPUnknownScope.logger.info("copy from url: " + urlSt);
            URL url = new URL(urlSt);
            InputStream input = url.openStream();
            File dir = JavaCIPUnknownScope.tempDir;
            File tempFile = new File(JavaCIPUnknownScope.tempDir + File.separator + JavaCIPUnknownScope.fileName(url));
            JavaCIPUnknownScope.logger.info("created: " + tempFile.getAbsolutePath());
            JavaCIPUnknownScope.copyFile(tempFile, input);
            return tempFile;
        } catch (IORuntimeException e) {
            throw new CaughtRuntimeException(e, JavaCIPUnknownScope.logger);
        }
    }
}
