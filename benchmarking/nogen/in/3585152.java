


class c3585152 {

    public static void copy(FileInputStream from, FileOutputStream to) throws IORuntimeException {
        FileChannel fromChannel = from.getChannel();
        FileChannel toChannel = to.getChannel();
        copy(fromChannel, toChannel);
        fromChannel.close();
        toChannel.close();
    }

}
