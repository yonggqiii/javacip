class c3008655 {

    public static void copyFile(File srcFile, File desFile) throws IORuntimeException {
        AssertUtility.notNull(srcFile);
        AssertUtility.notNull(desFile);
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(desFile);
        try {
            FileChannel srcChannel = fis.getChannel();
            FileChannel dstChannel = fos.getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } finally {
            fis.close();
            fos.close();
        }
    }
}
