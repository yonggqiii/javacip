


class c16378626 {

    public static void copy(String pstrFileFrom, String pstrFileTo) {
        try {
            FileChannel srcChannel = new FileInputStream(pstrFileFrom).getChannel();
            FileChannel dstChannel = new FileOutputStream(pstrFileTo).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
