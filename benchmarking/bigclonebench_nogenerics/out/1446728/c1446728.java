class c1446728 {

    private void backupFile(ZipOutputStream out, String base, String fn) throws IORuntimeException {
        String f = FileUtils.getAbsolutePath(fn);
        base = FileUtils.getAbsolutePath(base);
        if (!f.startsWith(base)) {
            Message.throwInternalError(f + " does not start with " + base);
        }
        f = f.substring(base.length());
        f = JavaCIPUnknownScope.correctFileName(f);
        out.putNextEntry(new ZipEntry(f));
        InputStream in = FileUtils.openFileInputStream(fn);
        IOUtils.copyAndCloseInput(in, out);
        out.closeEntry();
    }
}
