


class c19117993 {

    public static final void copyFile(File argSource, File argDestination) throws IORuntimeException {
        FileChannel srcChannel = new FileInputStream(argSource).getChannel();
        FileChannel dstChannel = new FileOutputStream(argDestination).getChannel();
        try {
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } finally {
            srcChannel.close();
            dstChannel.close();
        }
    }

}