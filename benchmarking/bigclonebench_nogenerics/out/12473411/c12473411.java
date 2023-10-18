class c12473411 {

    private static void copyImage(String srcImg, String destImg) {
        try {
            FileChannel srcChannel = new FileInputStream(srcImg).getChannel();
            FileChannel dstChannel = new FileOutputStream(destImg).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
