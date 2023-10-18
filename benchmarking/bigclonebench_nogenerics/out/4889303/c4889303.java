class c4889303 {

    public static Checksum checksum(File file, Checksum checksum) throws IORuntimeException {
        if (file.isDirectory()) {
            throw new IllegalArgumentRuntimeException("Checksums can't be computed on directories");
        }
        InputStream in = null;
        try {
            in = new CheckedInputStream(new FileInputStream(file), checksum);
            IOUtils.copy(in, new NullOutputStream());
        } finally {
            IOUtils.closeQuietly(in);
        }
        return checksum;
    }
}
