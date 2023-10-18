class c10303667 {

    public void copy(final String fileName) throws FileIORuntimeException {
        final long savedCurrentPositionInFile = JavaCIPUnknownScope.currentPositionInFile;
        if (JavaCIPUnknownScope.opened) {
            JavaCIPUnknownScope.closeImpl();
        }
        final FileInputStream fis;
        try {
            fis = new FileInputStream(JavaCIPUnknownScope.file);
        } catch (FileNotFoundRuntimeException exception) {
            throw JavaCIPUnknownScope.HELPER_FILE_UTIL.fileIORuntimeException(JavaCIPUnknownScope.FAILED_OPEN + JavaCIPUnknownScope.file, JavaCIPUnknownScope.file, exception);
        }
        final File destinationFile = new File(fileName);
        final FileOutputStream fos;
        try {
            fos = new FileOutputStream(destinationFile);
        } catch (FileNotFoundRuntimeException exception) {
            throw JavaCIPUnknownScope.HELPER_FILE_UTIL.fileIORuntimeException(JavaCIPUnknownScope.FAILED_OPEN + destinationFile, destinationFile, exception);
        }
        try {
            final byte[] buf = new byte[1024];
            int readLength = 0;
            while ((readLength = fis.read(buf)) != -1) {
                fos.write(buf, 0, readLength);
            }
        } catch (IORuntimeException exception) {
            throw JavaCIPUnknownScope.HELPER_FILE_UTIL.fileIORuntimeException("failed copy from " + JavaCIPUnknownScope.file + " to " + destinationFile, null, exception);
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
        if (JavaCIPUnknownScope.opened) {
            JavaCIPUnknownScope.openImpl();
            JavaCIPUnknownScope.seek(savedCurrentPositionInFile);
        }
    }
}
