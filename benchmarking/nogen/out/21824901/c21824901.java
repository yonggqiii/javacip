class c21824901 {

    public static void copyExternalResource(File sourceFile, File destFile) throws IORuntimeException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            JavaCIPUnknownScope.closeQuietly(source);
            JavaCIPUnknownScope.closeQuietly(destination);
        }
    }
}
