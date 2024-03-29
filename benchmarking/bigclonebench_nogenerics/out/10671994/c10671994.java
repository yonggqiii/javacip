class c10671994 {

    public static void copy(File src, File dst) {
        try {
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            srcChannel = null;
            dstChannel.close();
            dstChannel = null;
        } catch (IORuntimeException ex) {
            Tools.logRuntimeException(Tools.class, ex, dst.getAbsolutePath());
        }
    }
}
