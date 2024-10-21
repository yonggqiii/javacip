class c20051892 {

    public String fileUpload(final ResourceType type, final String currentFolder, final String fileName, final InputStream inputStream) throws InvalidCurrentFolderRuntimeException, WriteRuntimeException {
        String absolutePath = JavaCIPUnknownScope.getRealUserFilesAbsolutePath(RequestCycleHandler.getUserFilesAbsolutePath(ThreadLocalData.getRequest()));
        File typeDir = JavaCIPUnknownScope.getOrCreateResourceTypeDir(absolutePath, type);
        File currentDir = new File(typeDir, currentFolder);
        if (!currentDir.exists() || !currentDir.isDirectory())
            throw new InvalidCurrentFolderRuntimeException();
        File newFile = new File(currentDir, fileName);
        File fileToSave = UtilsFile.getUniqueFile(newFile.getAbsoluteFile());
        try {
            IOUtils.copyLarge(inputStream, new FileOutputStream(fileToSave));
        } catch (IORuntimeException e) {
            throw new WriteRuntimeException();
        }
        return fileToSave.getName();
    }
}
