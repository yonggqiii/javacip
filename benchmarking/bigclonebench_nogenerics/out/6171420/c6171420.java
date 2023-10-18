class c6171420 {

    public static void copyFile(File sourceFile, File destFile) throws IORuntimeException {
        JavaCIPUnknownScope.log.info("Copying file '" + sourceFile + "' to '" + destFile + "'");
        if (!sourceFile.isFile()) {
            throw new IllegalArgumentRuntimeException("The sourceFile '" + sourceFile + "' does not exist or is not a normal file.");
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            long numberOfBytes = destination.transferFrom(source, 0, source.size());
            JavaCIPUnknownScope.log.debug("Transferred " + numberOfBytes + " bytes from '" + sourceFile + "' to '" + destFile + "'.");
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
