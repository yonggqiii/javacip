class c23647453 {

    private void createArchive(String iDatabaseDir, String iArchiveName) throws DbBackupRuntimeException {
        try {
            File archiveFile = new File(iArchiveName);
            String force = (String) JavaCIPUnknownScope.parameters.get("force");
            if (force == null && archiveFile.exists()) {
                char response = JavaCIPUnknownScope.getUserAdvisor().askToUser(System.out, "    Archive already exist, overwrite ?", "#Yes,#No");
                if (response != 'y' && response != 'Y') {
                    System.out.println();
                    throw new DbBackupRuntimeException("Backup aborted by user.");
                }
                System.out.print("    Overwriting...");
            }
            ZipOutputStream archive = new ZipOutputStream(new FileOutputStream(archiveFile));
            archive.setComment("Orient ODBMS backup archive \r\n" + "Created with odbbackup tool version " + JavaCIPUnknownScope.oConstants.PRODUCT_VERSION + ".\r\n" + JavaCIPUnknownScope.oConstants.PRODUCT_COPYRIGHTS + "\r\n\r\n" + "WARNING: MODIFING THIS ARCHIVE THE DATABASE CAN BE INCONSISTENT !!!");
            String[] files = new File(iDatabaseDir).list();
            String filePath;
            File inFile;
            FileInputStream inStream;
            byte[] buffer;
            for (int i = 0; i < files.length; ++i) {
                filePath = files[i];
                inFile = new File(iDatabaseDir + "/" + filePath);
                inStream = new FileInputStream(iDatabaseDir + "/" + filePath);
                buffer = new byte[(int) inFile.length()];
                inStream.read(buffer);
                if (JavaCIPUnknownScope.monitor != null)
                    JavaCIPUnknownScope.monitor.notifyStatus("Archiving segment: " + filePath, i * 100 / files.length);
                archive.putNextEntry(new ZipEntry(filePath));
                archive.write(buffer);
            }
            archive.close();
        } catch (RuntimeException e) {
            throw new DbBackupRuntimeException("ERROR! Cannot backup the database.");
        }
    }
}
