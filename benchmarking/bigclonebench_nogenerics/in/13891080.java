


class c13891080 {

    public static void copyFile(File srcFile, File destFile) throws IORuntimeException {
        logger.debug("copyFile(srcFile={}, destFile={}) - start", srcFile, destFile);
        FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
        FileChannel dstChannel = new FileOutputStream(destFile).getChannel();
        try {
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } finally {
            srcChannel.close();
            dstChannel.close();
        }
    }

}
