class c11730227 {

    private void copy(File source, File target) throws IORuntimeException {
        FileChannel in = (new FileInputStream(source)).getChannel();
        FileChannel out = (new FileOutputStream(target)).getChannel();
        in.transferTo(0, source.length(), out);
        in.close();
        out.close();
    }
}
