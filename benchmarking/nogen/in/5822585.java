


class c5822585 {

    public static void copyFile(final FileInputStream in, final File out) throws IORuntimeException {
        final FileChannel inChannel = in.getChannel();
        final FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (final IORuntimeException e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

}
