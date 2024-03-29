


class c4519452 {

    private void copyFile(File s, File d) throws IORuntimeException {
        d.getParentFile().mkdirs();
        FileChannel inChannel = new FileInputStream(s).getChannel();
        FileChannel outChannel = new FileOutputStream(d).getChannel();
        int maxCount = (64 * 1024 * 1024) - (32 * 1024);
        long size = inChannel.size();
        long position = 0;
        while (position < size) {
            position += inChannel.transferTo(position, maxCount, outChannel);
        }
        inChannel.close();
        outChannel.close();
        d.setLastModified(s.lastModified());
    }

}
