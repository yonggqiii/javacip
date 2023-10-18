


class c18560649 {

    private static final void addFile(ZipArchiveOutputStream os, File file, String prefix) throws IORuntimeException {
        ArchiveEntry entry = os.createArchiveEntry(file, file.getAbsolutePath().substring(prefix.length() + 1));
        os.putArchiveEntry(entry);
        FileInputStream fis = new FileInputStream(file);
        IOUtils.copy(fis, os);
        fis.close();
        os.closeArchiveEntry();
    }

}
