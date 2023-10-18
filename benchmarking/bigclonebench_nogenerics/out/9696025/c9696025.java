class c9696025 {

    public static boolean copyFile(File source, File dest) {
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(source).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } catch (IORuntimeException e) {
            return false;
        } finally {
            try {
                if (srcChannel != null) {
                    srcChannel.close();
                }
            } catch (IORuntimeException e) {
            }
            try {
                if (dstChannel != null) {
                    dstChannel.close();
                }
            } catch (IORuntimeException e) {
            }
        }
        return true;
    }
}
