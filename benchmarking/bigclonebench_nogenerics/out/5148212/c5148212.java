class c5148212 {

    public static void copyFile(File src, File dest, int bufSize, boolean force) throws IORuntimeException {
        JavaCIPUnknownScope.logger.info("copyFile(File src=" + src + ", File dest=" + dest + ", int bufSize=" + bufSize + ", boolean force=" + force + ") - start");
        File f = new File(Configuration.getArchiveDir());
        if (!f.exists()) {
            f.mkdir();
        }
        if (dest.exists()) {
            if (force) {
                dest.delete();
            } else {
                throw new IORuntimeException("Cannot overwrite existing file: " + dest);
            }
        }
        byte[] buffer = new byte[bufSize];
        int read = 0;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            while (true) {
                read = in.read(buffer);
                if (read == -1) {
                    break;
                }
                out.write(buffer, 0, read);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }
        JavaCIPUnknownScope.logger.debug("copyFile(File, File, int, boolean) - end");
    }
}
