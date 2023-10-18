class c13361852 {

    public static File copyFile(File from, File to) throws IORuntimeException {
        FileOutputStream fos = new FileOutputStream(to);
        FileInputStream fis = new FileInputStream(from);
        FileChannel foc = fos.getChannel();
        FileChannel fic = fis.getChannel();
        foc.transferFrom(fic, 0, fic.size());
        foc.close();
        fic.close();
        return to;
    }
}
