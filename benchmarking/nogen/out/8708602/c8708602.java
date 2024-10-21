class c8708602 {

    public void execute(File temporaryFile) throws RuntimeException {
        ZipArchive archive = new ZipArchive(temporaryFile.getPath());
        InputStream input = archive.getInputFrom(JavaCIPUnknownScope.ARCHIVE_FILE_1);
        if (input != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copyAndClose(input, output);
            JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.ARCHIVE_FILE_1 + " contents not correct", JavaCIPUnknownScope.ARCHIVE_FILE_1_CONTENT, output.toString());
        } else {
            JavaCIPUnknownScope.fail("cannot open " + JavaCIPUnknownScope.ARCHIVE_FILE_1);
        }
    }
}
