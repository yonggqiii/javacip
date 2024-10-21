class c14885369 {

    public File copyFile(File in, File out) throws IORuntimeException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        JavaCIPUnknownScope.copyChannel(inChannel, outChannel);
        return out;
    }
}
