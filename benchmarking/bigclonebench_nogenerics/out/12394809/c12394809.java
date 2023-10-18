class c12394809 {

    public static final File getFile(final URL url) throws IORuntimeException {
        final File shortcutFile;
        final File currentFile = JavaCIPUnknownScope.files.get(url);
        if (currentFile == null || !currentFile.exists()) {
            shortcutFile = File.createTempFile("windowsIsLame", ".vbs");
            shortcutFile.deleteOnExit();
            JavaCIPUnknownScope.files.put(url, shortcutFile);
            final InputStream stream = url.openStream();
            final FileOutputStream out = new FileOutputStream(shortcutFile);
            try {
                StreamUtils.copy(stream, out);
            } finally {
                out.close();
                stream.close();
            }
        } else
            shortcutFile = currentFile;
        return shortcutFile;
    }
}