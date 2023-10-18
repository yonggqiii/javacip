class c2602107 {

    public OutputStream getAsOutputStream() throws IORuntimeException {
        OutputStream out;
        if (JavaCIPUnknownScope.contentStream != null) {
            File tmp = File.createTempFile(JavaCIPUnknownScope.getId(), null);
            out = new FileOutputStream(tmp);
            IOUtils.copy(JavaCIPUnknownScope.contentStream, out);
        } else {
            out = new ByteArrayOutputStream();
            out.write(JavaCIPUnknownScope.getContent());
        }
        return out;
    }
}
