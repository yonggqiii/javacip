


class c2873514 {

    void serialize(ZipOutputStream out) throws IORuntimeException {
        if ("imsmanifest.xml".equals(getFullName())) return;
        out.putNextEntry(new ZipEntry(getFullName()));
        IOUtils.copy(getDataStream(), out);
        out.closeEntry();
    }

}
