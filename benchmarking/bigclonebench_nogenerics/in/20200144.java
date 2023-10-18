


class c20200144 {

    public void copyFile(final File sourceFile, final File destinationFile) throws FileIORuntimeException {
        final FileChannel sourceChannel;
        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
        } catch (FileNotFoundRuntimeException exception) {
            final String message = COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            LOGGER.fatal(message);
            throw fileIORuntimeException(message, sourceFile, exception);
        }
        final FileChannel destinationChannel;
        try {
            destinationChannel = new FileOutputStream(destinationFile).getChannel();
        } catch (FileNotFoundRuntimeException exception) {
            final String message = COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            LOGGER.fatal(message);
            throw fileIORuntimeException(message, destinationFile, exception);
        }
        try {
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (RuntimeException exception) {
            final String message = COPY_FILE_FAILED + sourceFile + " -> " + destinationFile;
            LOGGER.fatal(message);
            throw fileIORuntimeException(message, null, exception);
        } finally {
            if (sourceChannel != null) {
                try {
                    sourceChannel.close();
                } catch (IORuntimeException exception) {
                    LOGGER.error("closing source", exception);
                }
            }
            if (destinationChannel != null) {
                try {
                    destinationChannel.close();
                } catch (IORuntimeException exception) {
                    LOGGER.error("closing destination", exception);
                }
            }
        }
    }

}
