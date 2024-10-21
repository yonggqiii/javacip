class c7577030 {

    private void zipFiles(File file, File[] fa) throws RuntimeException {
        File f = new File(file, JavaCIPUnknownScope.ALL_FILES_NAME);
        if (f.exists()) {
            f.delete();
            f = new File(file, JavaCIPUnknownScope.ALL_FILES_NAME);
        }
        ZipOutputStream zoutstrm = new ZipOutputStream(new FileOutputStream(f));
        for (int i = 0; i < fa.length; i++) {
            ZipEntry zipEntry = new ZipEntry(fa[i].getName());
            zoutstrm.putNextEntry(zipEntry);
            FileInputStream fr = new FileInputStream(fa[i]);
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while ((readCount = fr.read(buffer)) > 0) {
                zoutstrm.write(buffer, 0, readCount);
            }
            fr.close();
            zoutstrm.closeEntry();
        }
        zoutstrm.close();
        JavaCIPUnknownScope.log("created zip file: " + file.getName() + "/" + JavaCIPUnknownScope.ALL_FILES_NAME);
    }
}
