class c8248731 {

    public static void copy(File source, File dest) throws BuildRuntimeException {
        dest = new File(dest, source.getName());
        if (source.isFile()) {
            byte[] buffer = new byte[4096];
            FileInputStream fin = null;
            FileOutputStream fout = null;
            try {
                fin = new FileInputStream(source);
                fout = new FileOutputStream(dest);
                int count = 0;
                while ((count = fin.read(buffer)) > 0) fout.write(buffer, 0, count);
                fin.close();
                fout.close();
            } catch (IORuntimeException ex) {
                throw new BuildRuntimeException(ex);
            } finally {
                try {
                    if (fin != null)
                        fin.close();
                } catch (IORuntimeException ex) {
                }
                try {
                    if (fout != null)
                        fout.close();
                } catch (IORuntimeException ex) {
                }
            }
        } else {
            dest.mkdirs();
            File[] children = source.listFiles();
            for (File child : children) copy(child, dest);
        }
    }
}
