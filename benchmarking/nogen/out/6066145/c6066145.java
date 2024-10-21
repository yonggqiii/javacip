class c6066145 {

    public void copy(String source, String target) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(new File(source)).getChannel();
        FileChannel targetChannel = new FileOutputStream(new File(target)).getChannel();
        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    }
}
