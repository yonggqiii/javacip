class c19812872 {

    public InputStream getEntry(String entryPath) throws IORuntimeException {
        if (!JavaCIPUnknownScope.entries.contains(entryPath)) {
            return null;
        }
        JarInputStream jis = new JarInputStream(new BufferedInputStream(JavaCIPUnknownScope.url.openStream()));
        do {
            ZipEntry ze = jis.getNextEntry();
            if (ze == null) {
                break;
            }
            if (ze.getName().equals(entryPath)) {
                return jis;
            }
        } while (true);
        assert (false);
        return null;
    }
}
