


class c13812091 {

    public static void copyFile(IPath fromFileName, IPath toFileName) throws IORuntimeException {
        File fromFile = fromFileName.toFile();
        File toFile = toFileName.toFile();
        if (!fromFile.exists()) throw new IORuntimeException("FileCopy: " + "no such source file: " + fromFileName);
        if (!fromFile.isFile()) throw new IORuntimeException("FileCopy: " + "can't copy directory: " + fromFileName);
        if (!fromFile.canRead()) throw new IORuntimeException("FileCopy: " + "source file is unreadable: " + fromFileName);
        if (toFile.isDirectory()) toFile = new File(toFile, fromFile.getName());
        if (toFile.exists()) {
            if (!toFile.canWrite()) throw new IORuntimeException("FileCopy: " + "destination file is unwriteable: " + toFileName);
        } else {
            String parent = toFile.getParent();
            if (parent == null) parent = System.getProperty("user.dir");
            File dir = new File(parent);
            if (!dir.exists()) throw new IORuntimeException("FileCopy: " + "destination directory doesn't exist: " + parent);
            if (dir.isFile()) throw new IORuntimeException("FileCopy: " + "destination is not a directory: " + parent);
            if (!dir.canWrite()) throw new IORuntimeException("FileCopy: " + "destination directory is unwriteable: " + parent);
        }
        InputStream from = null;
        OutputStream to = null;
        try {
            from = new BufferedInputStream(new FileInputStream(fromFile));
            to = new BufferedOutputStream(new FileOutputStream(toFile));
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) to.write(buffer, 0, bytesRead);
        } finally {
            if (from != null) try {
                from.close();
            } catch (IORuntimeException e) {
            }
            if (to != null) try {
                to.close();
            } catch (IORuntimeException e) {
            }
        }
    }

}
