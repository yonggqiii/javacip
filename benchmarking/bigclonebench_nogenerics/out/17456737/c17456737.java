class c17456737 {

    public File addFile(File file, String suffix) throws IORuntimeException {
        if (file.exists() && file.isFile()) {
            File nf = File.createTempFile(JavaCIPUnknownScope.prefix, "." + suffix, JavaCIPUnknownScope.workdir);
            nf.delete();
            if (!file.renameTo(nf)) {
                IOUtils.copy(file, nf);
            }
            synchronized (JavaCIPUnknownScope.fileList) {
                JavaCIPUnknownScope.fileList.add(nf);
            }
            if (JavaCIPUnknownScope.log.isDebugEnabled()) {
                JavaCIPUnknownScope.log.debug("Add file [" + file.getPath() + "] -> [" + nf.getPath() + "]");
            }
            return nf;
        }
        return file;
    }
}
