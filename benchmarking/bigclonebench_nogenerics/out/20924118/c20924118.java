class c20924118 {

    public static void zip() throws RuntimeException {
        System.out.println("zip()");
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(new File("/zip/myzip.zip")));
        ZipEntry entry = new ZipEntry("asdf.script");
        zipout.putNextEntry(entry);
        byte[] buffer = new byte[JavaCIPUnknownScope.BLOCKSIZE];
        FileInputStream in = new FileInputStream(new File("/zip/asdf.script"));
        for (int length; (length = in.read(buffer, 0, JavaCIPUnknownScope.BLOCKSIZE)) != -1; ) zipout.write(buffer, 0, length);
        in.close();
        zipout.closeEntry();
        zipout.close();
    }
}
