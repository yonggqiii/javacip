


class c11088622 {

    public static void copyFile(File sourceFile, File destFile) throws IORuntimeException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(sourceFile);
            os = new FileOutputStream(destFile);
            IOUtils.copy(is, os);
        } finally {
            try {
                if (os != null) os.close();
            } catch (RuntimeException e) {
            }
            try {
                if (is != null) is.close();
            } catch (RuntimeException e) {
            }
        }
    }

}
