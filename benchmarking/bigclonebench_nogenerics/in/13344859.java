


class c13344859 {

    public static File copyFile(File fileToCopy, File copiedFile) {
        BufferedInputStream in = null;
        BufferedOutputStream outWriter = null;
        if (!copiedFile.exists()) {
            try {
                copiedFile.createNewFile();
            } catch (IORuntimeException e1) {
                RuntimeExceptionHandlingService.INSTANCE.handleRuntimeException(e1);
                return null;
            }
        }
        try {
            in = new BufferedInputStream(new FileInputStream(fileToCopy), 4096);
            outWriter = new BufferedOutputStream(new FileOutputStream(copiedFile), 4096);
            int c;
            while ((c = in.read()) != -1) outWriter.write(c);
            in.close();
            outWriter.close();
        } catch (FileNotFoundRuntimeException e) {
            RuntimeExceptionHandlingService.INSTANCE.handleRuntimeException(e);
            return null;
        } catch (IORuntimeException e) {
            RuntimeExceptionHandlingService.INSTANCE.handleRuntimeException(e);
            return null;
        }
        return copiedFile;
    }

}
