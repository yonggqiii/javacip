


class c14432775 {

    static void copyFile(File in, File outDir, String outFileName) throws IORuntimeException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        outDir.mkdirs();
        File outFile = new File(outDir, outFileName);
        FileChannel outChannel = new FileOutputStream(outFile).getChannel();
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
