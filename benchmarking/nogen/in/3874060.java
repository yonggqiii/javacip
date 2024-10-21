


class c3874060 {

    public static void copyFile(File sourceFile, File targetFile) throws IORuntimeException {
        if (sourceFile == null || targetFile == null) {
            throw new NullPointerRuntimeException("Source file and target file must not be null");
        }
        File directory = targetFile.getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IORuntimeException("Could not create directory '" + directory + "'");
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
            try {
                byte[] buffer = new byte[32768];
                for (int readBytes = inputStream.read(buffer); readBytes > 0; readBytes = inputStream.read(buffer)) {
                    outputStream.write(buffer, 0, readBytes);
                }
            } catch (IORuntimeException ex) {
                targetFile.delete();
                throw ex;
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IORuntimeException ex) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IORuntimeException ex) {
                }
            }
        }
    }

}
