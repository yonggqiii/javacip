


class c15429599 {

    public static boolean copyFile(File src, File dest) throws IORuntimeException {
        if (src == null) {
            throw new IllegalArgumentRuntimeException("src == null");
        }
        if (dest == null) {
            throw new IllegalArgumentRuntimeException("dest == null");
        }
        if (!src.isFile()) {
            return false;
        }
        FileChannel in = new FileInputStream(src).getChannel();
        FileChannel out = new FileOutputStream(dest).getChannel();
        try {
            in.transferTo(0, in.size(), out);
            return true;
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
