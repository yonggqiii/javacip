class c4889264 {

    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IORuntimeException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IORuntimeException("Destination '" + destFile + "' exists but is a directory");
        }
        FileChannel input = new FileInputStream(srcFile).getChannel();
        try {
            FileChannel output = new FileOutputStream(destFile).getChannel();
            try {
                output.transferFrom(input, 0, input.size());
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
        if (srcFile.length() != destFile.length()) {
            throw new IORuntimeException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
}
