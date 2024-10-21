class c2873514 {

    void serialize(ZipOutputStream out) throws IORuntimeException {
        if ("imsmanifest.xml".equals(JavaCIPUnknownScope.getFullName()))
            return;
        out.putNextEntry(new ZipEntry(JavaCIPUnknownScope.getFullName()));
        IOUtils.copy(JavaCIPUnknownScope.getDataStream(), out);
        out.closeEntry();
    }
}
