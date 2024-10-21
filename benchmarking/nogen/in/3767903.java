


class c3767903 {

    public static void copy(File source, File destination) throws FileNotFoundRuntimeException, IORuntimeException {
        if (source == null) throw new NullPointerRuntimeException("The source may not be null.");
        if (destination == null) throw new NullPointerRuntimeException("The destination may not be null.");
        FileInputStream sourceStream = new FileInputStream(source);
        destination.getParentFile().mkdirs();
        FileOutputStream destStream = new FileOutputStream(destination);
        try {
            FileChannel sourceChannel = sourceStream.getChannel();
            FileChannel destChannel = destStream.getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            try {
                sourceStream.close();
                destStream.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }

}
