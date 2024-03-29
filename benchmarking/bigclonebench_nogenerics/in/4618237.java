


class c4618237 {

    public static void writeFileToFile(File fin, File fout, boolean append) throws IORuntimeException {
        FileChannel inChannel = new FileInputStream(fin).getChannel();
        FileChannel outChannel = new FileOutputStream(fout, append).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) try {
                inChannel.close();
            } catch (IORuntimeException ex) {
            }
            if (outChannel != null) try {
                outChannel.close();
            } catch (IORuntimeException ex) {
            }
        }
    }

}
