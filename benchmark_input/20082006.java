


class c20082006 {

    public static void copyFile(File src, File dst) throws IOException {
        FileChannel sourceChannel = new FileInputStream(src).getChannel();
        FileChannel destinationChannel = new FileOutputStream(dst).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }

}
