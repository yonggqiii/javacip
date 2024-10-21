class c22046596 {

    public void copy(File s, File t) throws IORuntimeException {
        FileChannel in = (new FileInputStream(s)).getChannel();
        FileChannel out = (new FileOutputStream(t)).getChannel();
        in.transferTo(0, s.length(), out);
        in.close();
        out.close();
    }
}
