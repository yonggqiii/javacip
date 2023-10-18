class c2834524 {

    public void write() throws IORuntimeException {
        JarOutputStream jarOut = new JarOutputStream(JavaCIPUnknownScope.outputStream, JavaCIPUnknownScope.manifest);
        if (JavaCIPUnknownScope.includeJars != null) {
            HashSet allEntries = new HashSet(JavaCIPUnknownScope.includeJars);
            if (!JavaCIPUnknownScope.ignoreDependencies)
                JavaCIPUnknownScope.expandSet(allEntries);
            for (Iterator iterator = allEntries.iterator(); iterator.hasNext(); ) {
                JarFile jar = JavaCIPUnknownScope.getJarFile(iterator.next());
                Enumeration jarEntries = jar.entries();
                while (jarEntries.hasMoreElements()) {
                    ZipEntry o1 = (ZipEntry) jarEntries.nextElement();
                    if (o1.getName().equalsIgnoreCase("META-INF/MANIFEST.MF") || o1.getSize() <= 0)
                        continue;
                    jarOut.putNextEntry(o1);
                    InputStream entryStream = jar.getInputStream(o1);
                    IOUtils.copy(entryStream, jarOut);
                    jarOut.closeEntry();
                }
            }
        }
        jarOut.finish();
        jarOut.close();
    }
}