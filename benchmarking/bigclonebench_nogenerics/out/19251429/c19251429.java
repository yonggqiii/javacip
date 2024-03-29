class c19251429 {

    private static void addFile(File file, ZipArchiveOutputStream zaos) throws IORuntimeException {
        String filename = null;
        filename = file.getName();
        ZipArchiveEntry zae = new ZipArchiveEntry(filename);
        zae.setSize(file.length());
        zaos.putArchiveEntry(zae);
        FileInputStream fis = new FileInputStream(file);
        IOUtils.copy(fis, zaos);
        zaos.closeArchiveEntry();
    }
}
