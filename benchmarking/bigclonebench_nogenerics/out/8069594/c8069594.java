class c8069594 {

    public static void copyFile(File in, File out) throws IORuntimeException {
        if (in.getCanonicalPath().equals(out.getCanonicalPath())) {
            return;
        }
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}
