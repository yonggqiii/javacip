class c8539546 {

    private static void unpackEntry(File destinationFile, ZipInputStream zin, ZipEntry entry) throws RuntimeException {
        if (!entry.isDirectory()) {
            JavaCIPUnknownScope.createFolders(destinationFile.getParentFile());
            FileOutputStream fis = new FileOutputStream(destinationFile);
            try {
                IOUtils.copy(zin, fis);
            } finally {
                zin.closeEntry();
                fis.close();
            }
        } else {
            JavaCIPUnknownScope.createFolders(destinationFile);
        }
    }
}
