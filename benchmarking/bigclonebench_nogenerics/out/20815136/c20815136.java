class c20815136 {

    public static void copyTo(File src, File dest) throws IORuntimeException {
        if (src.equals(dest))
            throw new IORuntimeException("copyTo src==dest file");
        FileOutputStream fout = new FileOutputStream(dest);
        InputStream in = new FileInputStream(src);
        IOUtils.copyTo(in, fout);
        fout.flush();
        fout.close();
        in.close();
    }
}
