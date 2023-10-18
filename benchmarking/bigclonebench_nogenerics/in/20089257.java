


class c20089257 {

    public void store(String path, InputStream stream) throws IORuntimeException {
        toIgnore.add(normalizePath(path));
        ZipEntry entry = new ZipEntry(path);
        zipOutput.putNextEntry(entry);
        IOUtils.copy(stream, zipOutput);
        zipOutput.closeEntry();
    }

}
