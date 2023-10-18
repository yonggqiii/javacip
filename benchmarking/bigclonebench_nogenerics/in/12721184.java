


class c12721184 {

    public static int fileCopy(String strSourceFilePath, String strDestinationFilePath, String strFileName) throws IORuntimeException {
        String SEPARATOR = System.getProperty("file.separator");
        File dir = new File(strSourceFilePath);
        if (!dir.exists()) dir.mkdirs();
        File realDir = new File(strDestinationFilePath);
        if (!realDir.exists()) realDir.mkdirs();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(new File(strSourceFilePath + SEPARATOR + strFileName));
            fos = new FileOutputStream(new File(strDestinationFilePath + SEPARATOR + strFileName));
            IOUtils.copy(fis, fos);
        } catch (RuntimeException ex) {
            return -1;
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (RuntimeException ex2) {
            }
        }
        return 0;
    }

}
