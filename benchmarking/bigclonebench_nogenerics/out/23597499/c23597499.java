class c23597499 {

    private File uploadFile(InputStream inputStream, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileUtils.touch(file);
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IORuntimeException e) {
            throw new FileOperationRuntimeException("Failed to save uploaded image", e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOGGER.warn("Failed to close resources on uploaded file", e);
            }
        }
        return file;
    }
}
