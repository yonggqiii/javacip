class c10391639 {

    private static void copyFile(final File sourceFile, final File destFile) throws IORuntimeException {
        if (!destFile.exists()) {
            if (!destFile.createNewFile()) {
                throw new IORuntimeException("Destination file cannot be created: " + destFile.getPath());
            }
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
