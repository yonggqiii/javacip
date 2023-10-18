class c22712288 {

    public static void fileCopy(String from_name, String to_name) throws IORuntimeException {
        File fromFile = new File(from_name);
        File toFile = new File(to_name);
        if (fromFile.equals(toFile))
            JavaCIPUnknownScope.abort("cannot copy on itself: " + from_name);
        if (!fromFile.exists())
            JavaCIPUnknownScope.abort("no such currentSourcepartName file: " + from_name);
        if (!fromFile.isFile())
            JavaCIPUnknownScope.abort("can't copy directory: " + from_name);
        if (!fromFile.canRead())
            JavaCIPUnknownScope.abort("currentSourcepartName file is unreadable: " + from_name);
        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());
        if (toFile.exists()) {
            if (!toFile.canWrite())
                JavaCIPUnknownScope.abort("destination file is unwriteable: " + to_name);
        } else {
            String parent = toFile.getParent();
            if (parent == null)
                JavaCIPUnknownScope.abort("destination directory doesn't exist: " + parent);
            File dir = new File(parent);
            if (!dir.exists())
                JavaCIPUnknownScope.abort("destination directory doesn't exist: " + parent);
            if (dir.isFile())
                JavaCIPUnknownScope.abort("destination is not a directory: " + parent);
            if (!dir.canWrite())
                JavaCIPUnknownScope.abort("destination directory is unwriteable: " + parent);
        }
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ((bytes_read = from.read(buffer)) != -1) to.write(buffer, 0, bytes_read);
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IORuntimeException e) {
                    ;
                }
            if (to != null)
                try {
                    to.close();
                } catch (IORuntimeException e) {
                    ;
                }
        }
    }
}
