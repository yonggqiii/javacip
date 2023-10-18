class c20089257 {

    public void store(String path, InputStream stream) throws IORuntimeException {
        JavaCIPUnknownScope.toIgnore.add(JavaCIPUnknownScope.normalizePath(path));
        ZipEntry entry = new ZipEntry(path);
        JavaCIPUnknownScope.zipOutput.putNextEntry(entry);
        IOUtils.copy(stream, JavaCIPUnknownScope.zipOutput);
        JavaCIPUnknownScope.zipOutput.closeEntry();
    }
}
