


class c8132219 {

    public static void copyFile(String inFile, String outFile) {
        File in = new File(inFile);
        File out = new File(outFile);
        try {
            FileChannel inChannel = new FileInputStream(in).getChannel();
            FileChannel outChannel = new FileOutputStream(out).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } finally {
                if (inChannel != null) inChannel.close();
                if (outChannel != null) outChannel.close();
            }
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
