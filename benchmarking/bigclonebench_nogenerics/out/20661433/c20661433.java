class c20661433 {

    public static void copyFile(String fromFilePath, String toFilePath, boolean overwrite) throws IORuntimeException {
        File fromFile = new File(fromFilePath);
        File toFile = new File(toFilePath);
        if (!fromFile.exists())
            throw new IORuntimeException("FileCopy: " + "no such source file: " + fromFilePath);
        if (!fromFile.isFile())
            throw new IORuntimeException("FileCopy: " + "can't copy directory: " + fromFilePath);
        if (!fromFile.canRead())
            throw new IORuntimeException("FileCopy: " + "source file is unreadable: " + fromFilePath);
        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());
        if (toFile.exists()) {
            if (!overwrite) {
                throw new IORuntimeException(toFilePath + " already exists!");
            }
            if (!toFile.canWrite()) {
                throw new IORuntimeException("FileCopy: destination file is unwriteable: " + toFilePath);
            }
            String parent = toFile.getParent();
            if (parent == null) {
                parent = System.getProperty("user.dir");
            }
            File dir = new File(parent);
            if (!dir.exists()) {
                throw new IORuntimeException("FileCopy: destination directory doesn't exist: " + parent);
            }
            if (dir.isFile()) {
                throw new IORuntimeException("FileCopy: destination is not a directory: " + parent);
            }
            if (!dir.canWrite()) {
                throw new IORuntimeException("FileCopy: destination directory is unwriteable: " + parent);
            }
        }
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) to.write(buffer, 0, bytesRead);
        } finally {
            long lastModified = fromFile.lastModified();
            toFile.setLastModified(lastModified);
            if (from != null) {
                try {
                    from.close();
                } catch (IORuntimeException e) {
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
