class c5297820 {

    public static void copyFile(File src, File dst) throws IORuntimeException {
        FileChannel from = new FileInputStream(src).getChannel();
        FileChannel to = new FileOutputStream(dst).getChannel();
        from.transferTo(0, src.length(), to);
        from.close();
        to.close();
    }
}
