class c12524506 {

    public static void copyFile(File in, File outDir) throws IORuntimeException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(in).getChannel();
            File out = new File(outDir, in.getName());
            destinationChannel = new FileOutputStream(out).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            try {
                if (sourceChannel != null) {
                    sourceChannel.close();
                }
            } finally {
                if (destinationChannel != null) {
                    destinationChannel.close();
                }
            }
        }
    }
}
