class c16001374 {

    static void copyFile(File file, File destDir) {
        File destFile = new File(destDir, file.getName());
        if (destFile.exists() && (!destFile.canWrite())) {
            throw new SyncRuntimeException("Cannot overwrite " + destFile + " because " + "it is read-only");
        }
        try {
            FileInputStream in = new FileInputStream(file);
            try {
                FileOutputStream out = new FileOutputStream(destFile);
                try {
                    byte[] buffer = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } catch (IORuntimeException e) {
            throw new SyncRuntimeException("I/O error copying " + file + " to " + destDir + " (message: " + e.getMessage() + ")", e);
        }
        if (!destFile.setLastModified(file.lastModified())) {
            throw new SyncRuntimeException("Could not set last modified timestamp " + "of " + destFile);
        }
    }
}
