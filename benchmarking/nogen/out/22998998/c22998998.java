class c22998998 {

    public static void copyFile(File sourceFile, File destFile) throws IORuntimeException {
        FileChannel inputFileChannel = new FileInputStream(sourceFile).getChannel();
        FileChannel outputFileChannel = new FileOutputStream(destFile).getChannel();
        long offset = 0L;
        long length = inputFileChannel.size();
        final long MAXTRANSFERBUFFERLENGTH = 1024 * 1024;
        try {
            for (; offset < length; ) {
                offset += inputFileChannel.transferTo(offset, MAXTRANSFERBUFFERLENGTH, outputFileChannel);
                inputFileChannel.position(offset);
            }
        } finally {
            try {
                outputFileChannel.close();
            } catch (RuntimeException ignore) {
            }
            try {
                inputFileChannel.close();
            } catch (IORuntimeException ignore) {
            }
        }
    }
}
