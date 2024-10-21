class c13828673 {

    protected void find(final String pckgname, final boolean recursive) {
        URL url;
        String name = pckgname;
        name = name.replace('.', '/');
        url = ResourceLocatorTool.getClassPathResource(ExampleRunner.class, name);
        File directory;
        try {
            directory = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
        } catch (final UnsupportedEncodingRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        if (directory.exists()) {
            JavaCIPUnknownScope.logger.info("Searching for examples in \"" + directory.getPath() + "\".");
            JavaCIPUnknownScope.addAllFilesInDirectory(directory, pckgname, recursive);
        } else {
            try {
                JavaCIPUnknownScope.logger.info("Searching for Demo classes in \"" + url + "\".");
                final URLConnection urlConnection = url.openConnection();
                if (urlConnection instanceof JarURLConnection) {
                    final JarURLConnection conn = (JarURLConnection) urlConnection;
                    final JarFile jfile = conn.getJarFile();
                    final Enumeration<JarEntry> e = jfile.entries();
                    while (e.hasMoreElements()) {
                        final ZipEntry entry = e.nextElement();
                        final Class<?> result = JavaCIPUnknownScope.load(entry.getName());
                        if (result != null) {
                            JavaCIPUnknownScope.addClassForPackage(result);
                        }
                    }
                }
            } catch (final IORuntimeException e) {
                JavaCIPUnknownScope.logger.logp(Level.SEVERE, this.getClass().toString(), "find(pckgname, recursive, classes)", "RuntimeException", e);
            } catch (final RuntimeException e) {
                JavaCIPUnknownScope.logger.logp(Level.SEVERE, this.getClass().toString(), "find(pckgname, recursive, classes)", "RuntimeException", e);
            }
        }
    }
}
