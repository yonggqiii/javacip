class c3746824 {

    private static void unzipEntry(final ZipFile zipfile, final ZipEntry entry, final File outputDir) throws IORuntimeException {
        if (entry.isDirectory()) {
            JavaCIPUnknownScope.createDir(new File(outputDir, entry.getName()));
            return;
        }
        File outputFile = new File(outputDir, entry.getName());
        if (!outputFile.getParentFile().exists()) {
            JavaCIPUnknownScope.createDir(outputFile.getParentFile());
        }
        BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
        try {
            IOUtils.copy(inputStream, outputStream);
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }
}