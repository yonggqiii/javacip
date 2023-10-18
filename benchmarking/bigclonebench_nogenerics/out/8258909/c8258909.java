class c8258909 {

    public static void copyFile(File sourceFile, File destFile) {
        FileChannel source = null;
        FileChannel destination = null;
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
