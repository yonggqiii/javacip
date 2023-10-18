class c21322699 {

    public void zipUp() throws PersistenceRuntimeException {
        ZipOutputStream out = null;
        try {
            if (!JavaCIPUnknownScope.backup.exists())
                JavaCIPUnknownScope.backup.createNewFile();
            out = new ZipOutputStream(new FileOutputStream(JavaCIPUnknownScope.backup));
            out.setLevel(Deflater.DEFAULT_COMPRESSION);
            for (String file : JavaCIPUnknownScope.backupDirectory.list()) {
                JavaCIPUnknownScope.logger.debug("Deflating: " + file);
                FileInputStream in = null;
                try {
                    in = new FileInputStream(new File(JavaCIPUnknownScope.backupDirectory, file));
                    out.putNextEntry(new ZipEntry(file));
                    IOUtils.copy(in, out);
                } finally {
                    out.closeEntry();
                    if (null != in)
                        in.close();
                }
            }
            FileUtils.deleteDirectory(JavaCIPUnknownScope.backupDirectory);
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.logger.error("Unable to ZIP the backup {" + JavaCIPUnknownScope.backupDirectory.getAbsolutePath() + "}.", ex);
            throw new PersistenceRuntimeException(ex);
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.logger.error("Unable to close ZIP output stream.", e);
            }
        }
    }
}
