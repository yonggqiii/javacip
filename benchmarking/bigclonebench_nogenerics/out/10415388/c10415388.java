class c10415388 {

    public static void copyFileNIO(String src, String dst) {
        try {
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
