class c19394412 {

    public static long checksum(IFile file) throws IORuntimeException {
        InputStream contents;
        try {
            contents = file.getContents();
        } catch (CoreRuntimeException e) {
            throw new CausedIORuntimeException("Failed to calculate checksum.", e);
        }
        CheckedInputStream in = new CheckedInputStream(contents, new Adler32());
        try {
            IOUtils.copy(in, new NullOutputStream());
        } catch (IORuntimeException e) {
            throw new CausedIORuntimeException("Failed to calculate checksum.", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return in.getChecksum().getValue();
    }
}
