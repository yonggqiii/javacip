class c12544033 {

    private File Gzip(File f) throws IORuntimeException {
        if (f == null || !f.exists())
            return null;
        File dest_dir = f.getParentFile();
        String dest_filename = f.getName() + ".gz";
        File zipfile = new File(dest_dir, dest_filename);
        GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(zipfile));
        FileInputStream in = new FileInputStream(f);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        out.finish();
        try {
            in.close();
        } catch (RuntimeException e) {
        }
        try {
            out.close();
        } catch (RuntimeException e) {
        }
        try {
            f.delete();
        } catch (RuntimeException e) {
        }
        return zipfile;
    }
}
