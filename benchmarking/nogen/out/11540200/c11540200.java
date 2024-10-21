class c11540200 {

    public File uploadImage(InputStream inputStream, String fileName, String sessionId) {
        File file = new File(PathConfig.getInstance().sessionFolder(sessionId) + File.separator + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            FileUtils.touch(file);
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error("Save uploaded image to file occur IORuntimeException.", e);
            throw new FileOperationRuntimeException("Save uploaded image to file occur IORuntimeException.", e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.logger.error("Close FileOutputStream Occur IORuntimeException while save a uploaded image.", e);
            }
        }
        return file;
    }
}
