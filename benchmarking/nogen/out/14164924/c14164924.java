class c14164924 {

    public void copy(File source, File dest) throws IORuntimeException {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = (new FileInputStream(source)).getChannel();
            out = (new FileOutputStream(dest)).getChannel();
            in.transferTo(0, source.length(), out);
        } catch (FileNotFoundRuntimeException e) {
            throw new IORuntimeException("Wrong source or destination path for backup operation!");
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
