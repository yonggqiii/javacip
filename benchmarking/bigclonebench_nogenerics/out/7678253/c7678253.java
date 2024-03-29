class c7678253 {

    protected void copyFile(File sourceFile, File targetFile) throws FileNotFoundRuntimeException, IORuntimeException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(sourceFile).getChannel();
            outChannel = new FileOutputStream(targetFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            try {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
