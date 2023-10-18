class c4579115 {

    public void copy(String original, String copy) throws SQLRuntimeException {
        try {
            OutputStream out = JavaCIPUnknownScope.openFileOutputStream(copy, false);
            InputStream in = JavaCIPUnknownScope.openFileInputStream(original);
            IOUtils.copyAndClose(in, out);
        } catch (IORuntimeException e) {
            throw Message.convertIORuntimeException(e, "Can not copy " + original + " to " + copy);
        }
    }
}
