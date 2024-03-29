class c4208869 {

    public static void putNextJarEntry(JarOutputStream modelStream, String name, File file) throws IORuntimeException {
        JarEntry entry = new JarEntry(name);
        entry.setSize(file.length());
        modelStream.putNextEntry(entry);
        InputStream fileStream = new BufferedInputStream(new FileInputStream(file));
        IOUtils.copy(fileStream, modelStream);
        fileStream.close();
    }
}
