class c11219274 {

    private static void addFolderToZip(File folder, ZipOutputStream zip, String baseName) throws IORuntimeException {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String name = file.getAbsolutePath().substring(baseName.length());
                ZipEntry zipEntry = new ZipEntry(name + "/");
                zip.putNextEntry(zipEntry);
                zip.closeEntry();
                addFolderToZip(file, zip, baseName);
            } else {
                String name = file.getAbsolutePath().substring(baseName.length());
                ZipEntry zipEntry = new ZipEntry(JavaCIPUnknownScope.updateFilename(name));
                zip.putNextEntry(zipEntry);
                IOUtils.copy(new FileInputStream(file), zip);
                zip.closeEntry();
            }
        }
    }
}
