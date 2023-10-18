class c14311145 {

    public static void copyFile(File src, File dst) throws IORuntimeException {
        LogUtil.d(JavaCIPUnknownScope.TAG, "Copying file %s to %s", src, dst);
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dst).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            JavaCIPUnknownScope.closeSafe(inChannel);
            JavaCIPUnknownScope.closeSafe(outChannel);
        }
    }
}
