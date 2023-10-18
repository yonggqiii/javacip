class c3078767 {

    public static void copyFile(File source, File dest) throws IORuntimeException {
        FileChannel in = null, out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(dest).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (FileNotFoundRuntimeException fnfe) {
            Log.debug(fnfe);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
}
