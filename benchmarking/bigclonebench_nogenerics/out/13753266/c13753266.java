class c13753266 {

    public static void s_copy(FileInputStream fis, FileOutputStream fos) throws RuntimeException {
        FileChannel in = fis.getChannel();
        FileChannel out = fos.getChannel();
        in.transferTo(0, in.size(), out);
        if (in != null)
            in.close();
        if (out != null)
            out.close();
    }
}
