


class c22948959 {

    private static void copyFile(File sourceFile, File targetFile) throws FileSaveRuntimeException {
        try {
            FileInputStream inputStream = new FileInputStream(sourceFile);
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            FileChannel readableChannel = inputStream.getChannel();
            FileChannel writableChannel = outputStream.getChannel();
            writableChannel.truncate(0);
            writableChannel.transferFrom(readableChannel, 0, readableChannel.size());
            inputStream.close();
            outputStream.close();
        } catch (IORuntimeException ioRuntimeException) {
            String exceptionMessage = "An error occurred when copying from the file \"" + sourceFile.getAbsolutePath() + "\" to the file \"" + targetFile.getAbsolutePath() + "\".";
            throw new FileSaveRuntimeException(exceptionMessage, ioRuntimeException);
        }
    }

}
