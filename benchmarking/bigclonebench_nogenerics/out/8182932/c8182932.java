class c8182932 {

    public static void unzip(File file, ZipFile zipFile, File targetDirectory) throws BusinessRuntimeException {
        JavaCIPUnknownScope.LOG.info("Unzipping zip file '" + file.getAbsolutePath() + "' to directory '" + targetDirectory.getAbsolutePath() + "'.");
        assert (file.exists() && file.isFile());
        if (targetDirectory.exists() == false) {
            JavaCIPUnknownScope.LOG.debug("Creating target directory.");
            if (targetDirectory.mkdirs() == false) {
                throw new BusinessRuntimeException("Could not create target directory at '" + targetDirectory.getAbsolutePath() + "'!");
            }
        }
        ZipInputStream zipin = null;
        try {
            zipin = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;
            while ((entry = zipin.getNextEntry()) != null) {
                JavaCIPUnknownScope.LOG.debug("Unzipping entry '" + entry.getName() + "'.");
                if (entry.isDirectory()) {
                    JavaCIPUnknownScope.LOG.debug("Skipping directory.");
                    continue;
                }
                final File targetFile = new File(targetDirectory, entry.getName());
                final File parentTargetFile = targetFile.getParentFile();
                if (parentTargetFile.exists() == false) {
                    JavaCIPUnknownScope.LOG.debug("Creating directory '" + parentTargetFile.getAbsolutePath() + "'.");
                    if (parentTargetFile.mkdirs() == false) {
                        throw new BusinessRuntimeException("Could not create target directory at '" + parentTargetFile.getAbsolutePath() + "'!");
                    }
                }
                InputStream input = null;
                FileOutputStream output = null;
                try {
                    input = zipFile.getInputStream(entry);
                    if (targetFile.createNewFile() == false) {
                        throw new BusinessRuntimeException("Could not create target file '" + targetFile.getAbsolutePath() + "'!");
                    }
                    output = new FileOutputStream(targetFile);
                    int readBytes = 0;
                    byte[] buffer = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
                    while ((readBytes = input.read(buffer, 0, buffer.length)) > 0) {
                        output.write(buffer, 0, readBytes);
                    }
                } finally {
                    FileUtil.closeCloseable(input);
                    FileUtil.closeCloseable(output);
                }
            }
        } catch (IORuntimeException e) {
            throw new BusinessRuntimeException("Could not unzip file '" + file.getAbsolutePath() + "'!", e);
        } finally {
            FileUtil.closeCloseable(zipin);
        }
    }
}
