


class c15854397 {

    public static Checksum checksum(File file, Checksum checksum) throws IORuntimeException {
        if (file.isDirectory()) {
            throw new IllegalArgumentRuntimeException("Checksums can't be computed on directories");
        }
        InputStream in = null;
        try {
            in = new CheckedInputStream(new FileInputStream(file), checksum);
            IOUtils.copy(in, NULL_OUTPUT_STREAM);
        } finally {
            IOUtils.close(in);
        }
        return checksum;
    }

}
