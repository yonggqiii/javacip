class c21585794 {

    public static void copyFile(File source, File dest) throws IORuntimeException {
        if (source.equals(dest))
            return;
        FileChannel srcChannel = new FileInputStream(source).getChannel();
        FileChannel dstChannel = new FileOutputStream(dest).getChannel();
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        srcChannel.close();
        dstChannel.close();
    }
}
