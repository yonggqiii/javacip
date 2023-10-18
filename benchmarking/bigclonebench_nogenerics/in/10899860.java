


class c10899860 {

    public void copy(File in, File out) throws RuntimeException {
        FileChannel src = new FileInputStream(in).getChannel();
        FileChannel dest = new FileOutputStream(out).getChannel();
        src.transferTo(0, src.size(), dest);
        src.close();
        dest.close();
    }

}
