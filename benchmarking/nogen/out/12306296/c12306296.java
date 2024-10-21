class c12306296 {

    public static void copyFile(File source, File destination, long copyLength) throws IORuntimeException {
        if (!source.exists()) {
            String message = "File " + source + " does not exist";
            throw new FileNotFoundRuntimeException(message);
        }
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            JavaCIPUnknownScope.forceMkdir(destination.getParentFile());
        }
        if (destination.exists() && !destination.canWrite()) {
            String message = "Unable to open file " + destination + " for writing.";
            throw new IORuntimeException(message);
        }
        if (source.getCanonicalPath().equals(destination.getCanonicalPath())) {
            String message = "Unable to write file " + source + " on itself.";
            throw new IORuntimeException(message);
        }
        if (copyLength == 0) {
            JavaCIPUnknownScope.truncateFile(destination, 0);
        }
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(destination);
            long lengthLeft = copyLength;
            byte[] buffer = new byte[(int) Math.min(JavaCIPUnknownScope.BUFFER_LENGTH, lengthLeft + 1)];
            int read;
            while (lengthLeft > 0) {
                read = input.read(buffer);
                if (read == -1) {
                    break;
                }
                lengthLeft -= read;
                output.write(buffer, 0, read);
            }
            output.flush();
            output.getFD().sync();
        } finally {
            IOUtil.closeQuietly(input);
            IOUtil.closeQuietly(output);
        }
        destination.setLastModified(source.lastModified());
    }
}
