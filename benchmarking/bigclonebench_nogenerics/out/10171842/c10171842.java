class c10171842 {

    public String upload() throws IORuntimeException {
        int idx = JavaCIPUnknownScope.docIndex.incrementAndGet();
        String tmpName = "namefinder/doc_" + idx + "__" + JavaCIPUnknownScope.fileFileName;
        File tmpFile = JavaCIPUnknownScope.tmpFile(tmpName);
        if (tmpFile.exists()) {
            JavaCIPUnknownScope.org.apache.commons.io.FileUtils.deleteQuietly(tmpFile);
        }
        JavaCIPUnknownScope.org.apache.commons.io.FileUtils.touch(tmpFile);
        InputStream fileStream = new FileInputStream(JavaCIPUnknownScope.file);
        OutputStream bos = new FileOutputStream(tmpFile);
        IOUtils.copy(fileStream, bos);
        bos.close();
        fileStream.close();
        return JavaCIPUnknownScope.tmpUrl(tmpName);
    }
}
