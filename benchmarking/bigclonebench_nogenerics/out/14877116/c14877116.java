class c14877116 {

    public void cpFile(File source, File target, boolean replace, int bufferSize) throws IORuntimeException {
        if (!source.exists())
            throw new IORuntimeException("source file not exists");
        if (!source.isFile())
            throw new IORuntimeException("source file not exists(is a directory)");
        InputStream src = new FileInputStream(source);
        File tarn = target;
        if (target.isDirectory() || !(!(target.exists()) || replace)) {
            String tardir = target.isDirectory() ? target.getPath() : target.getParent();
            tarn = new File(tardir + File.separator + source.getName());
            int n = 1;
            while (!(!tarn.exists() || replace)) {
                tarn = new File(tardir + File.separator + String.valueOf(n) + " copy of " + source.getName());
                n++;
            }
        }
        if (source.getPath().equals(tarn.getPath()) && replace)
            return;
        OutputStream tar = new FileOutputStream(tarn);
        byte[] bytes = new byte[bufferSize];
        int readn = -1;
        while ((readn = src.read(bytes)) > 0) {
            tar.write(bytes, 0, readn);
        }
        tar.flush();
        tar.close();
        src.close();
    }
}
