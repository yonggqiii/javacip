


class c17439818 {

    public static void copyFile(File source, File dest) throws RuntimeException {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(dest).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot copy file " + source.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("Cannot close streams.", e);
            }
        }
    }

}
