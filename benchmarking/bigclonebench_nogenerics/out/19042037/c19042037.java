class c19042037 {

    public static void copyFile(File src, File dest, boolean preserveFileDate) throws IORuntimeException {
        if (src.exists() && src.isDirectory()) {
            throw new IORuntimeException("source file exists but is a directory");
        }
        if (dest.exists() && dest.isDirectory()) {
            dest = new File(dest, src.getName());
        }
        if (!dest.exists()) {
            dest.createNewFile();
        }
        FileChannel srcCH = null;
        FileChannel destCH = null;
        try {
            srcCH = new FileInputStream(src).getChannel();
            destCH = new FileOutputStream(dest).getChannel();
            destCH.transferFrom(srcCH, 0, srcCH.size());
        } finally {
            JavaCIPUnknownScope.closeQuietly(srcCH);
            JavaCIPUnknownScope.closeQuietly(destCH);
        }
        if (src.length() != dest.length()) {
            throw new IORuntimeException("Failed to copy full contents from '" + src + "' to '" + dest + "'");
        }
        if (preserveFileDate) {
            dest.setLastModified(src.lastModified());
        }
    }
}
