


class c18973294 {

    protected static File UrlToAFile(File target, String urlSt, String fileName) throws CaughtRuntimeException {
        try {
            logger.info("copy from url: " + urlSt);
            URL url = new URL(urlSt);
            InputStream input = url.openStream();
            File dir = tempDir;
            File tempFile = new File(target, fileName);
            logger.info("created: " + tempFile.getAbsolutePath());
            copyFile(tempFile, input);
            return tempFile;
        } catch (IORuntimeException e) {
            throw new CaughtRuntimeException(e, logger);
        }
    }

}
