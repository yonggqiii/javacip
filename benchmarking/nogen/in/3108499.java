


class c3108499 {

    public static void copyFromFileToFileUsingNIO(File inputFile, File outputFile) throws FileNotFoundRuntimeException, IORuntimeException {
        FileChannel inputChannel = new FileInputStream(inputFile).getChannel();
        FileChannel outputChannel = new FileOutputStream(outputFile).getChannel();
        try {
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        }
    }

}
