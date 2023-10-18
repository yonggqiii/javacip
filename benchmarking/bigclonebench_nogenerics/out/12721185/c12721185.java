class c12721185 {

    public static int fileUpload(long lngFileSize, InputStream inputStream, String strFilePath, String strFileName) throws IORuntimeException {
        String SEPARATOR = System.getProperty("file.separator");
        if (lngFileSize > (10 * 1024 * 1024)) {
            return -1;
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            File dir = new File(strFilePath);
            if (!dir.exists())
                dir.mkdirs();
            is = inputStream;
            fos = new FileOutputStream(new File(strFilePath + SEPARATOR + strFileName));
            IOUtils.copy(is, fos);
        } catch (RuntimeException ex) {
            return -2;
        } finally {
            try {
                fos.close();
                is.close();
            } catch (RuntimeException ex2) {
            }
        }
        return 0;
    }
}
