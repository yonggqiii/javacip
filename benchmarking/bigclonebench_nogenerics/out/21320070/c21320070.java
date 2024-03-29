class c21320070 {

    public static void copy(File from_file, File to_file) throws IORuntimeException {
        if (!from_file.exists()) {
            throw new IORuntimeException("FileCopy: no such source file: " + from_file.getPath());
        }
        if (!from_file.isFile()) {
            throw new IORuntimeException("FileCopy: can't copy directory: " + from_file.getPath());
        }
        if (!from_file.canRead()) {
            throw new IORuntimeException("FileCopy: source file is unreadable: " + from_file.getPath());
        }
        if (to_file.isDirectory()) {
            to_file = new File(to_file, from_file.getName());
        }
        if (to_file.exists()) {
            if (!to_file.canWrite()) {
                throw new IORuntimeException("FileCopy: destination file is unwriteable: " + to_file.getPath());
            }
            int choice = JOptionPane.showConfirmDialog(null, "Overwrite existing file " + to_file.getPath(), "File Exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) {
                throw new IORuntimeException("FileCopy: existing file was not overwritten.");
            }
        } else {
            String parent = to_file.getParent();
            if (parent == null) {
                parent = Globals.getDefaultPath();
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
            from = new FileInputStream(from_file);
            to = new FileOutputStream(to_file);
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ((bytes_read = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytes_read);
            }
        } finally {
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
