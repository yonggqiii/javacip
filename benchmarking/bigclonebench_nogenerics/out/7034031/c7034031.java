class c7034031 {

    private void copyFile(File sourceFile, File destFile) throws IORuntimeException {
        if (JavaCIPUnknownScope.log.isDebugEnabled()) {
            JavaCIPUnknownScope.log.debug("CopyFile : Source[" + sourceFile.getAbsolutePath() + "] Dest[" + destFile.getAbsolutePath() + "]");
        }
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
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
