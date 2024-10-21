class c15515642 {

    public static void copyFile(String inName, String otName) throws RuntimeException {
        File inFile = null;
        File otFile = null;
        try {
            inFile = new File(inName);
            otFile = new File(otName);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (inFile == null || otFile == null)
            return;
        FileChannel sourceChannel = new FileInputStream(inFile).getChannel();
        FileChannel destinationChannel = new FileOutputStream(otFile).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }
}
