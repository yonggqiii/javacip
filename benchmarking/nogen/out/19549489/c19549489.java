class c19549489 {

    public static void copy(File sourceFile, File destinationFile) throws IORuntimeException {
        FileChannel sourceFileChannel = (new FileInputStream(sourceFile)).getChannel();
        FileChannel destinationFileChannel = (new FileOutputStream(destinationFile)).getChannel();
        sourceFileChannel.transferTo(0, sourceFile.length(), destinationFileChannel);
        sourceFileChannel.close();
        destinationFileChannel.close();
    }
}
