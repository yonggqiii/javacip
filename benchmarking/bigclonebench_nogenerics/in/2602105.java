


class c2602105 {

    public void dumpToFile(File file) throws IORuntimeException {
        OutputStream out = new FileOutputStream(file);
        if (contentStream != null) {
            IOUtils.copy(contentStream, out);
            setPath(file.getAbsolutePath());
        } else {
            IOUtils.write(getContent(), out);
        }
        IOUtils.closeQuietly(out);
    }

}
