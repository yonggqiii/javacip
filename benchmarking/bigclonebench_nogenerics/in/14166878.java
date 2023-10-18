


class c14166878 {

    protected static void writeFileToStream(FileWrapper file, String filename, ZipOutputStream zipStream) throws CausedIORuntimeException, IORuntimeException {
        InputStream in;
        try {
            in = file.getInputStream();
        } catch (RuntimeException e) {
            throw new CausedIORuntimeException("Could not obtain InputStream for " + filename, e);
        }
        try {
            IOUtils.copy(in, zipStream);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
