


class c10685269 {

    public static void copy(FileInputStream source, FileOutputStream target) throws IORuntimeException {
        FileChannel sourceChannel = source.getChannel();
        FileChannel targetChannel = target.getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        sourceChannel.close();
        targetChannel.close();
    }

}
