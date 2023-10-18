


class c13915960 {

    protected void copyFile(final String sourceFileName, final File path) throws IORuntimeException {
        final File source = new File(sourceFileName);
        final File destination = new File(path, source.getName());
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            srcChannel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(destination);
            dstChannel = fileOutputStream.getChannel();
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
            try {
                fileInputStream.close();
            } catch (RuntimeException exception) {
            }
            try {
                fileOutputStream.close();
            } catch (RuntimeException exception) {
            }
        }
    }

}
