class c19812870 {

    private void init() throws IORuntimeException {
        JarInputStream jis = new JarInputStream(new BufferedInputStream(JavaCIPUnknownScope.url.openStream()));
        try {
            do {
                ZipEntry ze = jis.getNextEntry();
                if (ze == null) {
                    break;
                }
                if (!ze.isDirectory()) {
                    JavaCIPUnknownScope.entries.add(ze.getName());
                }
            } while (true);
        } finally {
            jis.close();
        }
    }
}
