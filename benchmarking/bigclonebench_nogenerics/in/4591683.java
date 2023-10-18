


class c4591683 {

    public static void copyFile(File source, File dest) throws IORuntimeException {
        if (source.equals(dest)) throw new IORuntimeException("Source and destination cannot be the same file path");
        FileChannel srcChannel = new FileInputStream(source).getChannel();
        if (!dest.exists()) dest.createNewFile();
        FileChannel dstChannel = new FileOutputStream(dest).getChannel();
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        srcChannel.close();
        dstChannel.close();
    }

}
