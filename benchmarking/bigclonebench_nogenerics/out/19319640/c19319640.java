class c19319640 {

    private void copy(File sourceFile, File destinationFile) {
        try {
            FileChannel in = new FileInputStream(sourceFile).getChannel();
            FileChannel out = new FileOutputStream(destinationFile).getChannel();
            try {
                in.transferTo(0, in.size(), out);
                in.close();
                out.close();
            } catch (IORuntimeException e) {
                TestLogger.error(e);
            }
        } catch (FileNotFoundRuntimeException e) {
            TestLogger.error(e);
        }
    }
}
