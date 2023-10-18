class c14058237 {

    public boolean renameTo(File dest) throws IORuntimeException {
        if (dest == null) {
            throw new NullPointerRuntimeException("dest");
        }
        if (!JavaCIPUnknownScope.file.renameTo(dest)) {
            FileInputStream inputStream = new FileInputStream(JavaCIPUnknownScope.file);
            FileOutputStream outputStream = new FileOutputStream(dest);
            FileChannel in = inputStream.getChannel();
            FileChannel out = outputStream.getChannel();
            long destsize = in.transferTo(0, JavaCIPUnknownScope.size, out);
            in.close();
            out.close();
            if (destsize == JavaCIPUnknownScope.size) {
                JavaCIPUnknownScope.file.delete();
                JavaCIPUnknownScope.file = dest;
                JavaCIPUnknownScope.isRenamed = true;
                return true;
            } else {
                dest.delete();
                return false;
            }
        }
        JavaCIPUnknownScope.file = dest;
        JavaCIPUnknownScope.isRenamed = true;
        return true;
    }
}
