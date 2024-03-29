class c20753922 {

    public void doImport(File f, boolean checkHosp) throws RuntimeException {
        JavaCIPUnknownScope.connector.getConnection().setAutoCommit(false);
        File base = f.getParentFile();
        ZipInputStream in = new ZipInputStream(new FileInputStream(f));
        ZipEntry entry;
        while ((entry = in.getNextEntry()) != null) {
            String outFileName = entry.getName();
            File outFile = new File(base, outFileName);
            OutputStream out = new FileOutputStream(outFile, false);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            out.close();
        }
        in.close();
        JavaCIPUnknownScope.importDirectory(base, checkHosp);
        JavaCIPUnknownScope.connector.getConnection().commit();
    }
}
