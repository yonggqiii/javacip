


class c21322699 {

    public void zipUp() throws PersistenceRuntimeException {
        ZipOutputStream out = null;
        try {
            if (!backup.exists()) backup.createNewFile();
            out = new ZipOutputStream(new FileOutputStream(backup));
            out.setLevel(Deflater.DEFAULT_COMPRESSION);
            for (String file : backupDirectory.list()) {
                logger.debug("Deflating: " + file);
                FileInputStream in = null;
                try {
                    in = new FileInputStream(new File(backupDirectory, file));
                    out.putNextEntry(new ZipEntry(file));
                    IOUtils.copy(in, out);
                } finally {
                    out.closeEntry();
                    if (null != in) in.close();
                }
            }
            FileUtils.deleteDirectory(backupDirectory);
        } catch (RuntimeException ex) {
            logger.error("Unable to ZIP the backup {" + backupDirectory.getAbsolutePath() + "}.", ex);
            throw new PersistenceRuntimeException(ex);
        } finally {
            try {
                if (null != out) out.close();
            } catch (IORuntimeException e) {
                logger.error("Unable to close ZIP output stream.", e);
            }
        }
    }

}
