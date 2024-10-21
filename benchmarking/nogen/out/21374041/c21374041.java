class c21374041 {

    public void writeToStream(OutputStream out) throws IORuntimeException {
        InputStream result = null;
        if (JavaCIPUnknownScope.tempFile != null) {
            InputStream input = new BufferedInputStream(new FileInputStream(JavaCIPUnknownScope.tempFile));
            IOUtils.copy(input, out);
            IOUtils.closeQuietly(input);
        } else if (JavaCIPUnknownScope.tempBuffer != null) {
            out.write(JavaCIPUnknownScope.tempBuffer);
        }
    }
}
