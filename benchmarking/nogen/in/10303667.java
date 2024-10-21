


class c10303667 {

    @Override
    public void copy(final String fileName) throws FileIORuntimeException {
        final long savedCurrentPositionInFile = currentPositionInFile;
        if (opened) {
            closeImpl();
        }
        final FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundRuntimeException exception) {
            throw HELPER_FILE_UTIL.fileIORuntimeException(FAILED_OPEN + file, file, exception);
        }
        final File destinationFile = new File(fileName);
        final FileOutputStream fos;
        try {
            fos = new FileOutputStream(destinationFile);
        } catch (FileNotFoundRuntimeException exception) {
            throw HELPER_FILE_UTIL.fileIORuntimeException(FAILED_OPEN + destinationFile, destinationFile, exception);
        }
        try {
            final byte[] buf = new byte[1024];
            int readLength = 0;
            while ((readLength = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLength);
            }
        } catch (IORuntimeException exception) {
            throw HELPER_FILE_UTIL.fileIORuntimeException("failed copy from " + file + " to " + destinationFile, null, exception);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (RuntimeException exception) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (RuntimeException exception) {
            }
        }
        if (opened) {
            openImpl();
            seek(savedCurrentPositionInFile);
        }
    }

}
