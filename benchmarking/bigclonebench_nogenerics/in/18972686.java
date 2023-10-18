


class c18972686 {

    public void jsFunction_addFile(ScriptableFile infile) throws IORuntimeException {
        if (!infile.jsFunction_exists()) throw new IllegalArgumentRuntimeException("Cannot add a file that doesn't exists to an archive");
        ZipArchiveEntry entry = new ZipArchiveEntry(infile.getName());
        entry.setSize(infile.jsFunction_getSize());
        out.putArchiveEntry(entry);
        try {
            InputStream inStream = infile.jsFunction_createInputStream();
            IOUtils.copy(inStream, out);
            inStream.close();
        } finally {
            out.closeArchiveEntry();
        }
    }

}
