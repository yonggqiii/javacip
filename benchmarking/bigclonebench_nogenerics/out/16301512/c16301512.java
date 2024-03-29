class c16301512 {

    public static void copy(File src, File dst) throws IORuntimeException {
        FileChannel inChannel;
        FileChannel outChannel;
        inChannel = new FileInputStream(src).getChannel();
        outChannel = new FileOutputStream(dst).getChannel();
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }
}
