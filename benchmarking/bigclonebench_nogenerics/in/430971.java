


class c430971 {

    public static void copyFile(File destFile, File src) throws IORuntimeException {
        File destDir = destFile.getParentFile();
        File tempFile = new File(destFile + "_tmp");
        destDir.mkdirs();
        InputStream is = new FileInputStream(src);
        try {
            FileOutputStream os = new FileOutputStream(tempFile);
            try {
                byte[] buf = new byte[8192];
                int len;
                while ((len = is.read(buf)) > 0) os.write(buf, 0, len);
            } finally {
                os.close();
            }
        } finally {
            is.close();
        }
        destFile.delete();
        if (!tempFile.renameTo(destFile)) throw new IORuntimeException("Unable to rename " + tempFile + " to " + destFile);
    }

}
