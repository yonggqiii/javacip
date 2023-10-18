class c16008963 {

    public static void unzip(final File file, final ZipFile zipFile, final File targetDirectory) throws PtRuntimeException {
        JavaCIPUnknownScope.LOG.info("Unzipping zip file '" + file.getAbsolutePath() + "' to directory " + "'" + targetDirectory.getAbsolutePath() + "'.");
        assert (file.exists() && file.isFile());
        if (targetDirectory.exists() == false) {
            JavaCIPUnknownScope.LOG.debug("Creating target directory.");
            if (targetDirectory.mkdirs() == false) {
                throw new PtRuntimeException("Could not create target directory at " + "'" + targetDirectory.getAbsolutePath() + "'!");
            }
        }
        ZipInputStream zipin = null;
        try {
            zipin = new ZipInputStream(new FileInputStream(file));
            ZipEntry nextZipEntry = zipin.getNextEntry();
            while (nextZipEntry != null) {
                JavaCIPUnknownScope.LOG.debug("Unzipping entry '" + nextZipEntry.getName() + "'.");
                if (nextZipEntry.isDirectory()) {
                    JavaCIPUnknownScope.LOG.debug("Skipping directory.");
                    continue;
                }
                final File targetFile = new File(targetDirectory, nextZipEntry.getName());
                final File parentTargetFile = targetFile.getParentFile();
                if (parentTargetFile.exists() == false) {
                    JavaCIPUnknownScope.LOG.debug("Creating directory '" + parentTargetFile.getAbsolutePath() + "'.");
                    if (parentTargetFile.mkdirs() == false) {
                        throw new PtRuntimeException("Could not create target directory at " + "'" + parentTargetFile.getAbsolutePath() + "'!");
                    }
                }
                InputStream input = null;
                FileOutputStream output = null;
                try {
                    input = zipFile.getInputStream(nextZipEntry);
                    if (targetFile.createNewFile() == false) {
                        throw new PtRuntimeException("Could not create target file " + "'" + targetFile.getAbsolutePath() + "'!");
                    }
                    output = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
                    int readBytes = input.read(buffer, 0, buffer.length);
                    while (readBytes > 0) {
                        output.write(buffer, 0, readBytes);
                        readBytes = input.read(buffer, 0, buffer.length);
                    }
                } finally {
                    PtCloseUtil.close(input, output);
                }
                nextZipEntry = zipin.getNextEntry();
            }
        } catch (IORuntimeException e) {
            throw new PtRuntimeException("Could not unzip file '" + file.getAbsolutePath() + "'!", e);
        } finally {
            PtCloseUtil.close(zipin);
        }
    }
}
