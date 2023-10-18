


class c6322440 {

    public static void copyFile(final File fromFile, File toFile) throws IORuntimeException {
        try {
            if (!fromFile.exists()) {
                throw new IORuntimeException("FileCopy: " + "no such source file: " + fromFile.getAbsoluteFile());
            }
            if (!fromFile.isFile()) {
                throw new IORuntimeException("FileCopy: " + "can't copy directory: " + fromFile.getAbsoluteFile());
            }
            if (!fromFile.canRead()) {
                throw new IORuntimeException("FileCopy: " + "source file is unreadable: " + fromFile.getAbsoluteFile());
            }
            if (toFile.isDirectory()) {
                toFile = new File(toFile, fromFile.getName());
            }
            if (toFile.exists() && !toFile.canWrite()) {
                throw new IORuntimeException("FileCopy: " + "destination file is unwriteable: " + toFile.getAbsoluteFile());
            }
            final FileChannel inChannel = new FileInputStream(fromFile).getChannel();
            final FileChannel outChannel = new FileOutputStream(toFile).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } catch (final IORuntimeException e) {
                throw e;
            } finally {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            }
        } catch (final IORuntimeException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("CopyFile went wrong!", e);
            }
        }
    }

}
