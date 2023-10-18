


class c15768167 {

    public static void copyFile(File in, File out) throws RuntimeException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(in).getChannel();
            destinationChannel = new FileOutputStream(out).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            if (sourceChannel != null) sourceChannel.close();
            if (destinationChannel != null) destinationChannel.close();
        }
    }

}
