


class c14230405 {

    public void copyFile(String from, String to) throws IORuntimeException {
        FileChannel srcChannel = new FileInputStream(from).getChannel();
        FileChannel dstChannel = new FileOutputStream(to).getChannel();
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        srcChannel.close();
        dstChannel.close();
    }

}
