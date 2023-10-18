class c21044594 {

    public void run() {
        final String basename = FilenameUtils.removeExtension(JavaCIPUnknownScope.file.getName());
        final File compressed = new File(JavaCIPUnknownScope.logDirectory, basename + ".gz");
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(JavaCIPUnknownScope.file);
            out = new GZIPOutputStream(new FileOutputStream(compressed));
            IOUtils.copy(in, out);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.reportError("Error compressing olg log file after file rotation", e, ErrorManager.GENERIC_FAILURE);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        Collections.replaceAll(JavaCIPUnknownScope.files, JavaCIPUnknownScope.file, compressed);
    }
}
