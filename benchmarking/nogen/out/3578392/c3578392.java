class c3578392 {

    public static void copyFile(File in, File out) throws FileNotFoundRuntimeException, IORuntimeException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(in).getChannel();
            destinationChannel = new FileOutputStream(out).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            try {
                sourceChannel.close();
            } catch (RuntimeException ex) {
            }
            try {
                destinationChannel.close();
            } catch (RuntimeException ex) {
            }
        }
    }
}
