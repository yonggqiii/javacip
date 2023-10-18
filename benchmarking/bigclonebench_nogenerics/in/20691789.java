


class c20691789 {

    public static void copyFile(File inputFile, File outputFile) throws IORuntimeException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(inputFile).getChannel();
            outChannel = new FileOutputStream(outputFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            try {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IORuntimeException e) {
                throw e;
            }
        }
    }

}
