class c21322698 {

    protected void unZip() throws PersistenceRuntimeException {
        boolean newZip = false;
        try {
            if (null == JavaCIPUnknownScope.backup) {
                JavaCIPUnknownScope.mode = (String) JavaCIPUnknownScope.context.get(Context.MODE);
                if (null == JavaCIPUnknownScope.mode)
                    JavaCIPUnknownScope.mode = Context.MODE_NAME_RESTORE;
                JavaCIPUnknownScope.backupDirectory = (File) JavaCIPUnknownScope.context.get(Context.BACKUP_DIRECTORY);
                JavaCIPUnknownScope.logger.debug("Got backup directory {" + JavaCIPUnknownScope.backupDirectory.getAbsolutePath() + "}");
                if (!JavaCIPUnknownScope.backupDirectory.exists() && JavaCIPUnknownScope.mode.equals(Context.MODE_NAME_BACKUP)) {
                    newZip = true;
                    JavaCIPUnknownScope.backupDirectory.mkdirs();
                } else if (!JavaCIPUnknownScope.backupDirectory.exists()) {
                    throw new PersistenceRuntimeException("Backup directory {" + JavaCIPUnknownScope.backupDirectory.getAbsolutePath() + "} does not exist.");
                }
                JavaCIPUnknownScope.backup = new File(JavaCIPUnknownScope.backupDirectory + "/" + JavaCIPUnknownScope.getBackupName() + ".zip");
                JavaCIPUnknownScope.logger.debug("Got zip file {" + JavaCIPUnknownScope.backup.getAbsolutePath() + "}");
            }
            File _explodedDirectory = File.createTempFile("exploded-" + JavaCIPUnknownScope.backup.getName() + "-", ".zip");
            _explodedDirectory.mkdirs();
            _explodedDirectory.delete();
            JavaCIPUnknownScope.backupDirectory = new File(_explodedDirectory.getParentFile(), _explodedDirectory.getName());
            JavaCIPUnknownScope.backupDirectory.mkdirs();
            JavaCIPUnknownScope.logger.debug("Created exploded directory {" + JavaCIPUnknownScope.backupDirectory.getAbsolutePath() + "}");
            if (!JavaCIPUnknownScope.backup.exists() && JavaCIPUnknownScope.mode.equals(Context.MODE_NAME_BACKUP)) {
                newZip = true;
                JavaCIPUnknownScope.backup.createNewFile();
            } else if (!JavaCIPUnknownScope.backup.exists()) {
                throw new PersistenceRuntimeException("Backup file {" + JavaCIPUnknownScope.backup.getAbsolutePath() + "} does not exist.");
            }
            if (newZip)
                return;
            ZipFile zip = new ZipFile(JavaCIPUnknownScope.backup);
            Enumeration zipFileEntries = zip.entries();
            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();
                JavaCIPUnknownScope.logger.debug("Inflating: " + entry);
                File destFile = new File(JavaCIPUnknownScope.backupDirectory, currentEntry);
                File destinationParent = destFile.getParentFile();
                destinationParent.mkdirs();
                if (!entry.isDirectory()) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zip.getInputStream(entry);
                        out = new FileOutputStream(destFile);
                        IOUtils.copy(in, out);
                    } finally {
                        if (null != out)
                            out.close();
                        if (null != in)
                            in.close();
                    }
                }
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error("Unable to unzip {" + JavaCIPUnknownScope.backup + "}", e);
            throw new PersistenceRuntimeException(e);
        }
    }
}
