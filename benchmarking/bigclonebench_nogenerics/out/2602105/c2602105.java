class c2602105 {

    public void dumpToFile(File file) throws IORuntimeException {
        OutputStream out = new FileOutputStream(file);
        if (JavaCIPUnknownScope.contentStream != null) {
            IOUtils.copy(JavaCIPUnknownScope.contentStream, out);
            JavaCIPUnknownScope.setPath(file.getAbsolutePath());
        } else {
            IOUtils.write(JavaCIPUnknownScope.getContent(), out);
        }
        IOUtils.closeQuietly(out);
    }
}
