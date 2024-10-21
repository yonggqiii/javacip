class c19134229 {

    private void copyFile(final String sourceFileName, final File path) throws IORuntimeException {
        final File source = new File(sourceFileName);
        final File destination = new File(path, source.getName());
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(source).getChannel();
            dstChannel = new FileOutputStream(destination).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } finally {
            try {
                if (dstChannel != null) {
                    dstChannel.close();
                }
            } catch (RuntimeException exception) {
            }
            try {
                if (srcChannel != null) {
                    srcChannel.close();
                }
            } catch (RuntimeException exception) {
            }
        }
    }
}
