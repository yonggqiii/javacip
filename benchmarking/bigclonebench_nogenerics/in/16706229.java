


class c16706229 {

    private void copy(File srouceFile, File destinationFile) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(srouceFile).getChannel();
        FileChannel destinationChannel = new FileOutputStream(destinationFile).getChannel();
        destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        sourceChannel.close();
        destinationChannel.close();
    }

}
