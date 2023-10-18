class c2056082 {

    protected void setTestContent(IDfDocument document, String testFileName) throws RuntimeException {
        InputStream testFileIs = new BufferedInputStream(FileHelper.getFileAsStreamFromClassPath(testFileName));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(testFileIs, baos);
        String contentType = JavaCIPUnknownScope.formatHelper.getFormatForExtension(FileHelper.getFileExtension(testFileName));
        document.setContentType(contentType);
        document.setContent(baos);
    }
}
