


class c10318446 {

    public static void copy(String sourceFile, String targetFile) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
        FileChannel targetChannel = new FileOutputStream(targetFile).getChannel();
        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        sourceChannel.close();
        targetChannel.close();
    }

}
