class c14408302 {

    public static void copyFile(File source, File destination) throws IORuntimeException {
        if (!source.isFile()) {
            throw new IORuntimeException(source + " is not a file.");
        }
        if (destination.exists()) {
            throw new IORuntimeException("Destination file " + destination + " is already exist.");
        }
        FileChannel inChannel = new FileInputStream(source).getChannel();
        FileChannel outChannel = new FileOutputStream(destination).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            inChannel.close();
            outChannel.close();
        }
    }
}
