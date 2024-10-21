class c12980227 {

    public void copy(File src, File dest) throws FileNotFoundRuntimeException, IORuntimeException {
        FileInputStream srcStream = new FileInputStream(src);
        FileOutputStream destStream = new FileOutputStream(dest);
        FileChannel srcChannel = srcStream.getChannel();
        FileChannel destChannel = destStream.getChannel();
        srcChannel.transferTo(0, srcChannel.size(), destChannel);
        destChannel.close();
        srcChannel.close();
        destStream.close();
        srcStream.close();
    }
}
