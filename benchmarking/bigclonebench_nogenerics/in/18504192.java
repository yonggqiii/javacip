


class c18504192 {

    static void copyFile(File in, File out) throws IORuntimeException {
        FileChannel source = new FileInputStream(in).getChannel();
        FileChannel destination = new FileOutputStream(out).getChannel();
        source.transferTo(0, source.size(), destination);
        source.close();
        destination.close();
    }

}
