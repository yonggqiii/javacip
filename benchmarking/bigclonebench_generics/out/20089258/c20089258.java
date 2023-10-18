class c20089258 {

    private void copyEntries() {
        if (JavaCIPUnknownScope.zipFile != null) {
            Enumeration<? extends ZipEntry> enumerator = JavaCIPUnknownScope.zipFile.entries();
            while (enumerator.hasMoreElements()) {
                ZipEntry entry = enumerator.nextElement();
                if (!entry.isDirectory() && !JavaCIPUnknownScope.toIgnore.contains(JavaCIPUnknownScope.normalizePath(entry.getName()))) {
                    ZipEntry originalEntry = new ZipEntry(entry.getName());
                    try {
                        JavaCIPUnknownScope.zipOutput.putNextEntry(originalEntry);
                        IOUtils.copy(JavaCIPUnknownScope.getInputStream(entry.getName()), JavaCIPUnknownScope.zipOutput);
                        JavaCIPUnknownScope.zipOutput.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
