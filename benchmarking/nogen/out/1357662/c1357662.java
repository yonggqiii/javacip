class c1357662 {

    private void copyFileTo(File destination) throws IORuntimeException {
        JavaCIPUnknownScope.logger.fine("Copying from " + destination + "...");
        FileChannel srcChannel = new FileInputStream(JavaCIPUnknownScope.getAbsolutePath()).getChannel();
        JavaCIPUnknownScope.logger.fine("...got source channel " + srcChannel + "...");
        FileChannel destChannel = new FileOutputStream(new File(destination.getAbsolutePath())).getChannel();
        JavaCIPUnknownScope.logger.fine("...got destination channel " + destChannel + "...");
        JavaCIPUnknownScope.logger.fine("...Got channels...");
        destChannel.transferFrom(srcChannel, 0, srcChannel.size());
        JavaCIPUnknownScope.logger.fine("...transferred.");
        srcChannel.close();
        destChannel.close();
    }
}
