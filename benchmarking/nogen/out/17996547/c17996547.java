class c17996547 {

    public static File copyFile(File fileToCopy, File copiedFile) {
        BufferedInputStream in = null;
        BufferedOutputStream outWriter = null;
        if (!copiedFile.exists()) {
            try {
                copiedFile.createNewFile();
            } catch (IORuntimeException e1) {
                e1.printStackTrace();
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
            e.printStackTrace();
            return null;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        }
        return copiedFile;
    }
}
