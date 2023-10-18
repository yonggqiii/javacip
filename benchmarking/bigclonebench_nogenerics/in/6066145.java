


class c6066145 {

    public void copy(String source, String target) throws IORuntimeException {
        @Cleanup FileChannel sourceChannel = new FileInputStream(new File(source)).getChannel();
        @Cleanup FileChannel targetChannel = new FileOutputStream(new File(target)).getChannel();
        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    }

}
