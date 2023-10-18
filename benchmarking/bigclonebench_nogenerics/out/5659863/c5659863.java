class c5659863 {

    public static FileChannel newFileChannel(File file, String rw, boolean enableRuntimeException) throws IORuntimeException {
        if (file == null)
            return null;
        if (rw == null || rw.length() == 0) {
            return null;
        }
        rw = rw.toLowerCase();
        if (rw.equals(JavaCIPUnknownScope.MODE_READ)) {
            if (FileUtil.exists(file, enableRuntimeException)) {
                FileInputStream fis = new FileInputStream(file);
                FileChannel ch = fis.getChannel();
                JavaCIPUnknownScope.setObjectMap(ch.hashCode(), fis, JavaCIPUnknownScope.FIS);
                return ch;
            }
        } else if (rw.equals(JavaCIPUnknownScope.MODE_WRITE)) {
            FileOutputStream fos = new FileOutputStream(file);
            FileChannel ch = fos.getChannel();
            JavaCIPUnknownScope.setObjectMap(ch.hashCode(), fos, JavaCIPUnknownScope.FOS_W);
            return ch;
        } else if (rw.equals(JavaCIPUnknownScope.MODE_APPEND)) {
            if (FileUtil.exists(file, enableRuntimeException)) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                FileChannel ch = raf.getChannel();
                ch.position(ch.size());
                JavaCIPUnknownScope.setObjectMap(ch.hashCode(), raf, JavaCIPUnknownScope.FOS_A);
                return ch;
            }
        } else if (rw.equals(JavaCIPUnknownScope.MODE_READ_WRITE)) {
            if (FileUtil.exists(file, enableRuntimeException)) {
                RandomAccessFile raf = new RandomAccessFile(file, rw);
                FileChannel ch = raf.getChannel();
                JavaCIPUnknownScope.setObjectMap(ch.hashCode(), raf, JavaCIPUnknownScope.RAF);
                return ch;
            }
        } else {
            throw new IllegalArgumentRuntimeException("Illegal read/write type : [" + rw + "]\n" + "You can use following types for: \n" + "  (1) Read Only  = \"r\"\n" + "  (2) Write Only = \"w\"\n" + "  (3) Read/Write = \"rw\"\n" + "  (4) Append     = \"a\"");
        }
        return null;
    }
}