class c2721034 {

    public File takeFile(File f, String prefix, String suffix) throws IORuntimeException {
        File nf = JavaCIPUnknownScope.createNewFile(prefix, suffix);
        FileInputStream fis = new FileInputStream(f);
        FileChannel fic = fis.getChannel();
        FileOutputStream fos = new FileOutputStream(nf);
        FileChannel foc = fos.getChannel();
        foc.transferFrom(fic, 0, fic.size());
        foc.close();
        fic.close();
        f.delete();
        return nf;
    }
}
