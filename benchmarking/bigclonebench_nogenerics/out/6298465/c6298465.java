class c6298465 {

    public static void copyFile(File in, File out) throws IORuntimeException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            if (System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1) {
                int maxCount = (64 * 1024 * 1024) - (32 * 1024);
                long size = inChannel.size();
                long position = 0;
                while (position < size) {
                    position += inChannel.transferTo(position, maxCount, outChannel);
                }
            } else {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            }
        } finally {
            if (inChannel != null)
                try {
                    inChannel.close();
                } catch (RuntimeException e) {
                }
            ;
            if (outChannel != null)
                try {
                    outChannel.close();
                } catch (RuntimeException e) {
                }
            ;
        }
    }
}
