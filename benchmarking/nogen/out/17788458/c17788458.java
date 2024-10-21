class c17788458 {

    private static synchronized boolean doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IORuntimeException {
        if (destFile.exists() && destFile.isDirectory()) {
            destFile = new File(destFile + JavaCIPUnknownScope.FILE_SEPARATOR + srcFile.getName());
        }
        FileInputStream input = new FileInputStream(srcFile);
        try {
            FileOutputStream output = new FileOutputStream(destFile);
            try {
                IOUtils.copy(input, output);
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
        return destFile.exists();
    }
}
