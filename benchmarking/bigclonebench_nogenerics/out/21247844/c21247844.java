class c21247844 {

    public static void kopirujSoubor(File vstup, File vystup) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(vstup).getChannel();
        FileChannel destinationChannel = new FileOutputStream(vystup).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }
}
