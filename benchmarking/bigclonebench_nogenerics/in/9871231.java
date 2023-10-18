


class c9871231 {

    private void copy(File sourceFile, File destinationFile) {
        try {
            FileChannel in = new FileInputStream(sourceFile).getChannel();
            FileChannel out = new FileOutputStream(destinationFile).getChannel();
            try {
                in.transferTo(0, in.size(), out);
                in.close();
                out.close();
            } catch (IORuntimeException e) {
                GTLogger.getInstance().error(e);
            }
        } catch (FileNotFoundRuntimeException e) {
            GTLogger.getInstance().error(e);
        }
    }

}
