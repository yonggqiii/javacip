class c18288467 {

    public static void copyFile(File in, File out) throws RuntimeException {
        Permissions before = JavaCIPUnknownScope.getFilePermissons(in);
        FileChannel inFile = new FileInputStream(in).getChannel();
        FileChannel outFile = new FileOutputStream(out).getChannel();
        inFile.transferTo(0, inFile.size(), outFile);
        inFile.close();
        outFile.close();
        JavaCIPUnknownScope.setFilePermissions(out, before);
    }
}
