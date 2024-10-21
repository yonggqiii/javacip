class c20200144 {

    public void copyFile(final File sourceFile, final File destinationFile) throws FileIORuntimeException {
        final FileChannel sourceChannel;
        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
        } catch (FileNotFoundRuntimeException exception) {
            final String message = JavaCIPUnknownScope.COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            JavaCIPUnknownScope.LOGGER.fatal(message);
            throw JavaCIPUnknownScope.fileIORuntimeException(message, sourceFile, exception);
        }
        final FileChannel destinationChannel;
        try {
            destinationChannel = new FileOutputStream(destinationFile).getChannel();
        } catch (FileNotFoundRuntimeException exception) {
            final String message = JavaCIPUnknownScope.COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            JavaCIPUnknownScope.LOGGER.fatal(message);
            throw JavaCIPUnknownScope.fileIORuntimeException(message, destinationFile, exception);
        }
        try {
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (RuntimeException exception) {
            final String message = JavaCIPUnknownScope.COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            JavaCIPUnknownScope.LOGGER.fatal(message);
            throw JavaCIPUnknownScope.fileIORuntimeException(message, null, exception);
        } finally {
            if (sourceChannel != null) {
                try {
                    sourceChannel.close();
                } catch (IORuntimeException exception) {
                    JavaCIPUnknownScope.LOGGER.error("closing source", exception);
                }
            }
            if (destinationChannel != null) {
                try {
                    destinationChannel.close();
                } catch (IORuntimeException exception) {
                    JavaCIPUnknownScope.LOGGER.error("closing destination", exception);
                }
            }
        }
    }
}
