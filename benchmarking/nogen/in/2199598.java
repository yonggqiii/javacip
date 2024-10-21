


class c2199598 {

    public static void copyFile(File file, String pathExport) throws IORuntimeException {
        File out = new File(pathExport);
        FileChannel sourceChannel = new FileInputStream(file).getChannel();
        FileChannel destinationChannel = new FileOutputStream(out).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }

}
