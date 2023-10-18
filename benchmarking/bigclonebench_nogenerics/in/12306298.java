


class c12306298 {

    public static void renameFileMultiFallback(File sourceFile, File destFile) throws FileHandlingRuntimeException {
        if (destFile.exists()) {
            throw new FileHandlingRuntimeException(FileHandlingRuntimeException.FILE_ALREADY_EXISTS);
        }
        if (!sourceFile.exists()) {
            return;
        }
        boolean succ = sourceFile.renameTo(destFile);
        if (succ) {
            NLogger.warn(FileUtils.class, "First renameTo operation worked!");
            return;
        }
        NLogger.warn(FileUtils.class, "First renameTo operation failed.");
        System.gc();
        Thread.yield();
        succ = sourceFile.renameTo(destFile);
        if (succ) {
            return;
        }
        NLogger.warn(FileUtils.class, "Second renameTo operation failed.");
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(sourceFile);
            output = new FileOutputStream(destFile);
            long lengthLeft = sourceFile.length();
            byte[] buffer = new byte[(int) Math.min(BUFFER_LENGTH, lengthLeft + 1)];
            int read;
            while (lengthLeft > 0) {
                read = input.read(buffer);
                if (read == -1) {
                    break;
                }
                lengthLeft -= read;
                output.write(buffer, 0, read);
            }
        } catch (IORuntimeException exp) {
            NLogger.warn(FileUtils.class, "Third renameTo operation failed.");
            throw new FileHandlingRuntimeException(FileHandlingRuntimeException.RENAME_FAILED, exp);
        } finally {
            IOUtil.closeQuietly(input);
            IOUtil.closeQuietly(output);
        }
        destFile.setLastModified(sourceFile.lastModified());
        FileUtils.deleteFileMultiFallback(sourceFile);
    }

}
