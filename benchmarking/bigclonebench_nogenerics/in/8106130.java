


class c8106130 {

    private void copyFile(File source, File dest) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(source).getChannel();
        FileChannel destinationChannel = new FileOutputStream(dest).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }

}
