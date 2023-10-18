class c14588787 {

    public void saveFile(final InputStream inputStream, final String fileName) {
        final File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(file);
            IOUtils.copy(inputStream, fileOut);
        } catch (FileNotFoundRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error("saveFile() - File Not Found." + e);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error("saveFile() - Error while saving file." + e);
        } finally {
            try {
                inputStream.close();
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOGGER.error(e);
            }
        }
    }
}
