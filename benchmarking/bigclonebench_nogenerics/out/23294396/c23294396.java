class c23294396 {

    public static void copyFile(File in, File out) throws IORuntimeException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileOutputStream fos = new FileOutputStream(out);
        FileChannel outChannel = fos.getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
            fos.flush();
            fos.close();
        }
    }
}
