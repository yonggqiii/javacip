class c18542300 {

    public static void copy(File src, File dest) throws IORuntimeException {
        FileChannel srcChannel = new FileInputStream(src).getChannel();
        FileChannel destChannel = new FileOutputStream(dest).getChannel();
        destChannel.transferFrom(srcChannel, 0, srcChannel.size());
        srcChannel.close();
        destChannel.close();
    }
}
