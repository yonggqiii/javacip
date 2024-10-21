class c18563163 {

    private void dumpFile(File repository, File copy) {
        try {
            if (copy.exists() && !copy.delete()) {
                throw new RuntimeRuntimeException("can't delete copy: " + copy);
            }
            JavaCIPUnknownScope.printFile("Real Archive File", repository);
            new ZipArchive(repository.getPath());
            IOUtils.copyFiles(repository, copy);
            JavaCIPUnknownScope.printFile("Copy Archive File", copy);
            new ZipArchive(copy.getPath());
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
