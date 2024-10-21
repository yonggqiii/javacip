class c7492253 {

    protected Connection openRelativeFile(String file) throws IORuntimeException {
        if (JavaCIPUnknownScope.cachedBits == null) {
            JavaCIPUnknownScope.cachedBits = new ByteArray(JavaCIPUnknownScope.url.openConnection().getInputStream()).getBytes();
        }
        ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(JavaCIPUnknownScope.cachedBits));
        ZipEntry zentry;
        while (true) {
            zentry = zin.getNextEntry();
            if (zentry == null) {
                throw new IORuntimeException("Couldn't find resource " + file + " in ZIP-file");
            }
            if (zentry.getName().equals(file)) {
                return new Connection(zin, zentry.getSize());
            }
        }
    }
}
